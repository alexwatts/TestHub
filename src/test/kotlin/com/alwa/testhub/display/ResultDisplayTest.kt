package com.alwa.testhub.display

import com.alwa.ObjectMother
import com.alwa.testhub.domain.Column
import com.alwa.testhub.domain.Image
import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.domain.Row
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

    private fun threeTestsThreeRunsDisplay(): ReportDisplay {
       val headerRow = Row("header", listOf(Column("2020-05-20T09:39:00", null), Column("2020-05-20T09:16:00", null), Column("2020-05-20T09:00:00", null)))
       val testOneRow = Row("test1", listOf(Column("failed", null), Column("empty", null), (Column("passed", null))))
       val testTwoRow = Row("test2", listOf(Column("failed", null), Column("failed", null), Column("failed", null)))
       val testThreeRow = Row("test3",listOf(Column("failed", null), Column("passed", null), Column("passed", null)))

       return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow, testTwoRow, testThreeRow))
    }

    private fun oneRunWithScreenshotDisplay(): ReportDisplay {
        val headerRow = Row("header", listOf(Column("2020-05-20T09:00:00", null)))
        val testOneRow = Row("test1", listOf(Column("passed", Image("screenshot", "mime/type", "htQWEC6543"))))
        return ReportDisplay(DEFAULT_ALL_GROUP, listOf(headerRow, testOneRow))
    }

}
