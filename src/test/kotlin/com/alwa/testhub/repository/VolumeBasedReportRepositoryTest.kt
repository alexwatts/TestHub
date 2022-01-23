package com.alwa.testhub.repository

import com.alwa.testhub.domain.ReportData
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class VolumeBasedReportRepositoryTest {
    val subject: VolumeBasedReportRepository =
        VolumeBasedReportRepository(
            System.getProperty("java.io.tmpdir") + "/results")

    val partition = "testPartition"
    val now = Instant.parse("2020-05-20T09:00:00Z")
    val later = Instant.parse("2020-05-20T09:30:00Z")
    val ealier = Instant.parse("2020-05-20T08:00:00Z")

    val reportData =
        ReportData(
            now,
            "{some: Json}",
            "testPartition")

    @Test
    fun saveAndFindReportBefore() {
        subject.create(reportData)
        val savedReport = subject.getBefore(later)
        assertThat(savedReport.get(partition)?.firstOrNull(), equalTo(reportData))
    }

    @Test
    fun saveAndFindReportAfter() {
        subject.create(reportData)
        val savedReport = subject.getAfter(ealier)
        assertThat(savedReport.get(partition)?.firstOrNull(), equalTo(reportData))
    }

    @Test
    fun saveAndDeletePartition() {
        subject.create(reportData)
        subject.delete(partition)
        val savedReport = subject.getBefore(later)
        assertThat(savedReport.get(partition)?.firstOrNull(), nullValue())
    }
}
