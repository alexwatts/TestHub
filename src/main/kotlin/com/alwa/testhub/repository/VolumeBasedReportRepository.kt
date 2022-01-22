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
class VolumeBasedReportRepository(
    @Value("\${storage.rootPath}") val rootPath: String): ReportRepository {

    override fun create(reportData: ReportData) {
        val reportDirectory = getReportDirectory(reportData.partition)
        createReportDirectoryIfMissing(reportDirectory)
        writeReport(reportData.report, reportDirectory, reportData.time)
    }

    override fun getBefore(before: Instant): Map<String, List<ReportData>> {
        return Files.walk(Path.of(rootPath))
            .filter { !Files.isDirectory(it) }
            .map { toReport(it.toFile()) }
            .filter { it.time.isBefore(before) }
            .collect(Collectors.toList())
            .groupBy { it.partition }
    }

    override fun getAfter(before: Instant): Map<String, List<ReportData>> {
        return Files.walk(Path.of(rootPath))
            .filter { !Files.isDirectory(it) }
            .map { toReport(it.toFile()) }
            .filter { it.time.isAfter(before) }
            .collect(Collectors.toList())
            .groupBy { it.partition }
    }

    override fun delete(partition: String) {
        Files.walk(Path.of("$rootPath/$partition"))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
    }

    private fun getReportDirectory(partition: String) = "$rootPath/$partition"

    private fun createReportDirectoryIfMissing(directory: String) =
        Files.createDirectories(Path.of(directory))

    private fun writeReport(report: String, directory: String, time: Instant) =
        Files.write(
            Path.of("$directory/$time"),
            report.toByteArray()
        )

    private fun toReport(file: File) =
        ReportData(
            Instant.parse(file.name),
            file.readText(UTF_8),
            file.lastPathPart()
        )

    private fun File.lastPathPart() =
        this.parent.takeLastWhile { !it.isFileSeparator() }

    private fun Char.isFileSeparator(): Boolean = this == File.separatorChar

}
