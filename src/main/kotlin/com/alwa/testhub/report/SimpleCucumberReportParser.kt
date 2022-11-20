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

    override fun parseTestResults(reportData: ReportData) =
        readTestNames(reportData)
            .map {
                TestResult(
                    it as String,
                    reportData.time,
                    testIsSuccess(reportData, it),
                    testScreenShot(reportData, it),
                    reportData.group,
                    emptyList(),
                    emptyList()
                )
            }

    private fun readTestNames(reportData: ReportData) =
        JsonPath.read<JSONArray>(reportData.report, jsonPathTestNames())

    private fun testIsSuccess(reportData: ReportData, testName: String) =
        JsonPath.read<JSONArray>(reportData.report, jsonPathFailedTests(testName)).isEmpty()

    private fun testScreenShot(reportData: ReportData, testName: String) =
        JsonPath.read<JSONArray>(reportData.report, jsonPathScreenShot(testName))
            .map { (it as LinkedHashMap<*, *>) }
            .map { ScreenShot(it["mime_type"] as String, it["data"] as String) }
            .firstOrNull()

    private fun jsonPathTestNames() = "\$.[*].elements.[*].name"

    private fun jsonPathFailedTests(testName: String) =
        "\$.[*].elements.[*].[?(@.name=='${testName}')].steps.[*].result[?(@.status=='failed')]"

    private fun jsonPathScreenShot(testName: String) =
        "\$.[*].elements.[*].[?(@.name=='${testName}')].after.[*].embeddings[?(@.name=='screenshot')]"

}