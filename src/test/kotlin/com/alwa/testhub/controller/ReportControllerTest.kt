package com.alwa.testhub.controller

import com.alwa.ObjectMother
import com.alwa.testhub.ReportTestConfiguration
import com.alwa.testhub.service.ReportService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ReportTestConfiguration::class)
class ReportControllerTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var reportService: ReportService

    private var headers: HttpHeaders = HttpHeaders()

    @BeforeEach
    fun setUp() {
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
    }

    @Test
    fun submitReportRespondsOk() {
        val report =  ObjectMother.report()
        val createdAccount = testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        assertThat(createdAccount.statusCode, equalTo(HttpStatus.OK))
    }

    @Test
    fun submitReportCreatesReport() {
        val report =  ObjectMother.report()
        testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        Mockito.verify(reportService).createReport(report, "test")
    }

    @Test
    fun getReportsRespondsOk() {
        val reports = testRestTemplate.exchange("/reports", HttpMethod.GET, HttpEntity(null, headers), String::class.java )
        assertThat(reports.statusCode, equalTo(HttpStatus.OK))
    }

    @Test
    fun getReportsGetsReports() {
        testRestTemplate.exchange("/reports", HttpMethod.GET, HttpEntity(null, headers), String::class.java )
        Mockito.verify(reportService).getReports()
    }

    @Test
    fun getDeleteRespondsOk() {
        val delete = testRestTemplate.exchange("/reports/test", HttpMethod.DELETE, HttpEntity(null, headers), String::class.java )
        assertThat(delete.statusCode, equalTo(HttpStatus.OK))
    }

    @Test
    fun deleteCallsDelete() {
        testRestTemplate.exchange("/reports/test", HttpMethod.DELETE, HttpEntity(null, headers), String::class.java )
        Mockito.verify(reportService).delete("test")
    }
}
