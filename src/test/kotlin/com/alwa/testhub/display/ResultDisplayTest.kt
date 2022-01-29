package com.alwa.testhub.display

import com.alwa.ObjectMother
import com.alwa.testhub.domain.Column
import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.domain.Row
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class ResultDisplayTest {

    val subject = ResultDisplay()

    @Test
    fun displaysResultsInRowsAndColumns() {
        val testResults = ObjectMother.threeTestsThreeRuns()
        val reportDisplay = subject.displayResults(testResults)
        assertThat(reportDisplay, equalTo(threeTestsThreeRunsDisplay()))
    }

    private fun threeTestsThreeRunsDisplay(): ReportDisplay {

       val headerRow = Row("header", listOf(Column("2020-05-20T09:39:00"), Column("2020-05-20T09:16:00"), Column("2020-05-20T09:00:00")))
       val testOneRow = Row("test1", listOf(Column("failed"), Column("empty"), (Column("passed"))))
       val testTwoRow = Row("test2", listOf(Column("failed"), Column("failed"), Column("failed")))
       val testThreeRow = Row("test3",listOf(Column("failed"), Column("passed"), Column("passed")))

       return ReportDisplay(listOf(headerRow, testOneRow, testTwoRow, testThreeRow))
    }
}
