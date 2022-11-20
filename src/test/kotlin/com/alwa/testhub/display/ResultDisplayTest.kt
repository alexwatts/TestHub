package com.alwa.testhub.display

import com.alwa.ObjectMother
import com.alwa.testhub.domain.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class ResultDisplayTest {

    val DEFAULT_ALL_GROUP = "default"

    val subject = ResultDisplay()

    @Test
    fun displaysResultsInRowsAndColumns() {
        val testResults = ObjectMother.threeTestsThreeRuns()
        val reportDisplay = subject.displayResults(DEFAULT_ALL_GROUP, testResults)
        assertThat(reportDisplay, equalTo(threeTestsThreeRunsDisplay()))
    }

    @Test
    fun displaysAScreenShotInAColumn() {
        val testResults = ObjectMother.singleRunWithScreenShot()
        val reportDisplay = subject.displayResults(DEFAULT_ALL_GROUP, testResults)
        assertThat(reportDisplay, equalTo(oneRunWithScreenshotDisplay()))
    }

    @Test
    fun displaysAMessageInAColumn() {
        val testResults = ObjectMother.singleRunWithMessage()
        val reportDisplay = subject.displayResults(DEFAULT_ALL_GROUP, testResults)
        assertThat(reportDisplay, equalTo(oneRunWithMessage()))
    }

    @Test
    fun displaysAPropertiesInAColumn() {
        val testResults = ObjectMother.singleRunWithProperties()
        val reportDisplay = subject.displayResults(DEFAULT_ALL_GROUP, testResults)
        assertThat(reportDisplay, equalTo(oneRunWithProperties()))
    }

    private fun threeTestsThreeRunsDisplay(): ReportDisplay {
       val headerRow = Row("header", listOf(Column("2020-05-20T09:39:00", null, emptyList(), emptyList()), Column("2020-05-20T09:16:00", null, emptyList(), emptyList()), Column("2020-05-20T09:00:00", null, emptyList(), emptyList())))
       val testOneRow = Row("test1", listOf(Column("failed", null, emptyList(), emptyList()), Column("empty", null, emptyList(), emptyList()), (Column("passed", null, emptyList(), emptyList()))))
       val testTwoRow = Row("test2", listOf(Column("failed", null, emptyList(), emptyList()), Column("failed", null, emptyList(), emptyList()), Column("failed", null, emptyList(), emptyList())))
       val testThreeRow = Row("test3",listOf(Column("failed", null, emptyList(), emptyList()), Column("passed", null, emptyList(), emptyList()), Column("passed", null, emptyList(), emptyList())))

       return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow, testTwoRow, testThreeRow))
    }

    private fun oneRunWithScreenshotDisplay(): ReportDisplay {
        val headerRow = Row("header", listOf(Column("2020-05-20T09:00:00", null, emptyList(), emptyList())))
        val testOneRow = Row("test1", listOf(Column("passed", Image("screenshot", "mime/type", "htQWEC6543"), emptyList(), emptyList())))
        return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow))
    }

    private fun oneRunWithMessage(): ReportDisplay {
        val headerRow = Row("header", listOf(Column("2020-05-20T09:00:00", null, emptyList(), emptyList())))
        val testOneRow = Row("test1", listOf(Column("failed", Image("screenshot", "mime/type", "htQWEC6543"), listOf("message"), emptyList())))
        return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow))
    }

    private fun oneRunWithProperties(): ReportDisplay {
        val headerRow = Row("header", listOf(Column("2020-05-20T09:00:00", null, emptyList(), emptyList())))
        val testOneRow = Row("test1", listOf(Column("failed", Image("screenshot", "mime/type", "htQWEC6543"), listOf("message"), listOf(
            Property("BUILD_ID", "4928")
        ))))
        return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow))
    }
}
