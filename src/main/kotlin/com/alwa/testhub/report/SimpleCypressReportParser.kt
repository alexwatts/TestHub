package com.alwa.testhub.report

import com.alwa.testhub.domain.Property
import com.alwa.testhub.domain.ReportData
import com.alwa.testhub.domain.TestResult
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory
import javax.xml.xpath.XPathNodes

@Service
@ConditionalOnProperty("report.cypress.parser", havingValue="simple")
class SimpleCypressReportParser: ReportParser {

    override fun parseTestResults(reportData: ReportData): List<TestResult> =
        (readTestNames(reportData).value() as XPathNodes)
            .map {
                TestResult(
                    it.textContent,
                    reportData.time,
                    testIsSuccess(reportData, it.textContent),
                    null,
                    reportData.group,
                    failureMessage(reportData, it.textContent),
                    testProperties(reportData)
                )
            }

    private fun readTestNames(reportData: ReportData) =
        XPathFactory.newInstance().newXPath().evaluateExpression(xPathTestNames(), readXml(reportData.report))

    private fun testIsSuccess(reportData: ReportData, testName: String) =
        (XPathFactory.newInstance().newXPath().evaluateExpression(xPathFailedTests(testName), readXml(reportData.report)).value() as XPathNodes).size() == 0

    private fun xPathTestNames() = "/testsuites/testsuite[@tests > 0]/testcase/@name"

    private fun xPathFailedTests(testName: String) =
        "/testsuites/testsuite[@tests > 0]/testcase[@name = '$testName' and failure]"

    private fun xPathFailedTestMessage(testName: String) =
        "/testsuites/testsuite[@tests > 0]/testcase[@name = '$testName' and failure]/failure/@message"

    private fun xPathProperties() = "/testsuites/testsuite/properties"

    private fun xPathFirstProperties() = "/testsuites/testsuite/properties[1]/property"

    private fun readXml(reportText: String) =
        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(InputSource(StringReader(reportText)))

    private fun failureMessage(reportData: ReportData, testName: String) =
        if (testIsSuccess(reportData, testName)) { emptyList() } else listOf(failedTestMessage(reportData, testName))

    private fun failedTestMessage(reportData: ReportData, testName: String) =
        (XPathFactory.newInstance().newXPath().evaluateExpression(xPathFailedTestMessage(testName), readXml(reportData.report)).value() as XPathNodes).first().nodeValue

    private fun testProperties(reportData: ReportData) =
       if (testHasProperties(reportData)) { firstProperties(reportData) } else emptyList()

    private fun firstProperties(reportData: ReportData) =
        (XPathFactory.newInstance().newXPath().evaluateExpression(xPathFirstProperties(), readXml(reportData.report)).value() as XPathNodes)
            .map {
                Property(
                    it.attributes.getNamedItem("name").textContent,
                    it.attributes.getNamedItem("value").textContent
                )
            }

    private fun testHasProperties(reportData: ReportData) =
        (XPathFactory.newInstance().newXPath().evaluateExpression(xPathProperties(), readXml(reportData.report)).value() as XPathNodes).size() > 0


}