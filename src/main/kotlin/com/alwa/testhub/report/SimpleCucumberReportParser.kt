package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.TestResult
import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.time.Instant

@Service
@ConditionalOnProperty("report.cucumber.parser", havingValue="simple")
class SimpleCucumberReportParser: ReportParser {

    override fun parseTestResults(reportData: ReportData): List<TestResult> {
        val tests: JSONArray = readTestNames(reportData)
        return tests.map { TestResult(it as String, reportData.time, testIsSuccess(reportData, it)) }
    }

    private fun readTestNames(reportData: ReportData): JSONArray {
        val JSON_PATH_TEST_NAMES = "\$.[*].elements.[*].name"
        return JsonPath.read(reportData.report, JSON_PATH_TEST_NAMES)
    }

    private fun testIsSuccess(reportData: ReportData, testName: String): Boolean {
        val JSON_PATH_FAILED_TEST_RESULT =
            "\$.[*].elements.[*].[?(@.name=='${testName}')].steps.[*].result[?(@.status=='failed')]"
        val failedTests: JSONArray = JsonPath.read(reportData.report, JSON_PATH_FAILED_TEST_RESULT)
        return failedTests.isEmpty()
    }

}
