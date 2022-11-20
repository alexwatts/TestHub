package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.ObjectMother
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class SimpleCypressReportParserTest {

    val subject = SimpleCypressReportParser()

    val reportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.cypressReport(),
            "testPartition")

    val failedReportData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.failedCypressReport(),
            "testPartition")

    val failedReportPropertyData =
        ReportData(
            Instant.parse("2020-05-20T09:00:00Z"),
            ObjectMother.failedCypressReportWithProperty(),
            "testPartition")

    val expectedTestNames = listOf("Sidebar Functionality")
    val expectedReportTime = listOf(Instant.parse("2020-05-20T09:00:00Z"))
    val PROPERTY_NAME = "BUILD_ID"

    @Test
    fun testNameIsSourcedFromReport() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.name }.toList(), equalTo(expectedTestNames))
    }

    @Test
    fun testReportTimeIsDisplayedAsSubmitted() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.reportTime }.toList(), equalTo(expectedReportTime))
    }

    @Test
    fun passingTestHasSuccessfulResult() {
        val testResults = subject.parseTestResults(reportData)
        assertThat(testResults.map { it.success }.first(), equalTo(true))
    }

    @Test
    fun failingTestHasFailedResult() {
        val testResults = subject.parseTestResults(failedReportData)
        assertThat(testResults.map { it.success }.first(), equalTo(false))
    }

    @Test
    fun failingTestRetrievesFailedMessage() {
        val testResults = subject.parseTestResults(failedReportData)
        assertThat(testResults.map { it.messages }.first(), equalTo(listOf("failed")))
    }

    @Test
    fun failingTestRetrievesProperty() {
        val testResults = subject.parseTestResults(failedReportPropertyData)
        assertThat(testResults.map { it.properties[0].value }, equalTo(listOf("4291")))
    }

}