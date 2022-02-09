package com.alwa.testhub.repository

import com.alwa.testhub.ReportTestConfiguration
import com.alwa.testhub.domain.ReportData
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ReportTestConfiguration::class)

class VolumeBasedReportRepositoryTest() {

    @Autowired
    lateinit var volumeBasedReportRepository: VolumeBasedReportRepository

    val partition = "testPartition"
    val now = Instant.parse("2020-05-20T09:00:00Z")
    val later = Instant.parse("2020-05-20T09:30:00Z")
    val muchLater = Instant.parse("2025-05-20T09:30:00Z")
    val earlier = Instant.parse("2020-05-20T08:00:00Z")

    val reportData =
        ReportData(
            now,
            "{some: Json}",
            "testPartition")

    @Test
    fun savedReportIsWritten() {
        volumeBasedReportRepository.create(reportData)
        val savedReport = volumeBasedReportRepository.getBefore(later)
        assertThat(savedReport.get(partition)?.firstOrNull(), equalTo(reportData))
    }

    @Test
    fun saveAndFindReportBeforeIsEmpty() {
        volumeBasedReportRepository.create(reportData)
        val savedReport = volumeBasedReportRepository.getBefore(earlier)
        assertThat(savedReport[partition], `is`(nullValue()))
    }

    @Test
    fun saveAndFindReportAfter() {
        volumeBasedReportRepository.create(reportData)
        val savedReport = volumeBasedReportRepository.getAfter(earlier)
        assertThat(savedReport.get(partition)?.firstOrNull(), equalTo(reportData))
    }

    @Test
    fun saveAndFindReportAfterIsEmpty() {
        volumeBasedReportRepository.create(reportData)
        val savedReport = volumeBasedReportRepository.getAfter(muchLater)
        assertThat(savedReport[partition], `is`(nullValue()))
    }

    @Test
    fun saveAndDeletePartition() {
        volumeBasedReportRepository.create(reportData)
        volumeBasedReportRepository.delete(partition)
        val savedReport = volumeBasedReportRepository.getBefore(later)
        assertThat(savedReport[partition]?.firstOrNull(), nullValue())
    }
}
