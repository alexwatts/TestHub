package com.alwa.testhub.repository

import com.alwa.testhub.domain.ReportData
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.util.stream.Collectors
import kotlin.text.Charsets.UTF_8

@Repository
@ConditionalOnProperty("storage.volume", havingValue="true")
class VolumeBasedReportRepository: ReportRepository {

    @Value("\${storage.rootPath}")
    lateinit var rootPath: String

    override fun create(reportData: ReportData) {
        writeReport(reportData.report, createReportFilePath(reportData))
    }

    override fun getBefore(before: Instant) =
        getReportsFiltered { it.time.isBefore(before) }

    override fun getAfter(after: Instant) =
        getReportsFiltered { it.time.isAfter(after) }

    override fun delete(group: String) =
        Files.walk(Path.of("$rootPath/$group"))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete)

    private fun createReportFilePath(reportData: ReportData) =
        Path.of(
            getReportDirectory(reportData),
            reportData.time.toString()
        )

    private fun getReportDirectory(reportData: ReportData) =
        createReportDirectory(reportDirectory(reportData)).toAbsolutePath().toString()

    private fun reportDirectory(reportData: ReportData) = "$rootPath/${reportData.group}"

    private fun getReportsFiltered(filter: (ReportData) -> (Boolean)) =
        Files.walk(Path.of(rootPath))
            .filter { !Files.isDirectory(it) }
            .map { it.toFile().toReport() }
            .filter { filter(it) }
            .collect(Collectors.toList())
            .groupBy { it.group }

    private fun createReportDirectory(directory: String) =
        Files.createDirectories(Path.of(directory))

    private fun writeReport(report: String, reportDirectorPath: Path) {
        Files.write(
            reportDirectorPath,
            report.toByteArray()
        )
    }

    private fun File.toReport() =
        ReportData(
            Instant.parse(this.name),
            this.readText(UTF_8),
            this.parent.substringAfterLast(File.separatorChar)
        )

}
