package com.alwa.testhub.report

import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.ScreenShot
import com.alwa.testhub.domain.TestResult
import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty("report.cucumber.parser", havingValue="simple")
class SimpleCucumberReportParser: ReportParser {

    fun JSON_PATH_TEST_NAMES() = "\$.[*].elements.[*].name"

    fun JSON_PATH_FAILED_TEST_RESULT(testName: String) =
        "\$.[*].elements.[*].[?(@.name=='${testName}')].steps.[*].result[?(@.status=='failed')]"

    fun JSON_PATH_SCREENSHOT(testName: String) =
        "\$.[*].elements.[*].[?(@.name=='${testName}')].after.[*].embeddings[?(@.name=='screenshot')]"

    override fun parseTestResults(reportData: ReportData): List<TestResult> =
        readTestNames(reportData)
            .map {
                TestResult(
                    it as String,
                    reportData.time,
                    testIsSuccess(reportData, it),
                    testScreenShot(reportData, it)
                )
            }

    private fun readTestNames(reportData: ReportData) =
        JsonPath.read<JSONArray>(reportData.report, JSON_PATH_TEST_NAMES())

    private fun testIsSuccess(reportData: ReportData, testName: String) =
        JsonPath.read<JSONArray>(reportData.report, JSON_PATH_FAILED_TEST_RESULT(testName)).isEmpty()

    private fun testScreenShot(reportData: ReportData, testName: String) =
        JsonPath.read<JSONArray>(reportData.report, JSON_PATH_SCREENSHOT(testName))
            .map { (it as LinkedHashMap<*, *>) }
            .map { ScreenShot(it["mime_type"] as String, it["data"] as String) }
            .first()

}




