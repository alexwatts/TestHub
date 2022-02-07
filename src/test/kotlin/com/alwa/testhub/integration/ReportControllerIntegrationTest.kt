package com.alwa.testhub.integration

import com.alwa.ObjectMother
import com.alwa.testhub.ReportTestConfiguration
import com.alwa.testhub.domain.ReportDisplay
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Clock
import java.time.Instant

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ReportTestConfiguration::class)
class ReportControllerIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var clock: Clock

    private var headers: HttpHeaders = HttpHeaders()

    @BeforeEach
    fun setUp() {
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)
    }

    @Test
    fun submitReportGetReportDisplay() {
        val report =  ObjectMother.report()
        testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        val exchange = testRestTemplate.exchange(
            "/reports", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<ReportDisplay>() {})
        assertThat(exchange.body ,equalTo(ObjectMother.displayReport("Test Example", Instant.parse("2021-11-20T09:00:00Z"))))
    }


    @Test
    fun deleteReportDeletesReport() {
        val report =  ObjectMother.report()
        testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        testRestTemplate.exchange("/reports/test", HttpMethod.DELETE, HttpEntity(report, headers), String::class.java )
        val exchange = testRestTemplate.exchange(
            "/reports", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<ReportDisplay>() {})
        assertThat(exchange.body ,equalTo(ObjectMother.emptyReport()))
    }

}
