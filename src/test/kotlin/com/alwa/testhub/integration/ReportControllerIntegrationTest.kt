package com.alwa.testhub.integration

import com.alwa.ObjectMother
import com.alwa.testhub.ReportTestConfiguration
import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.service.ReportService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.nio.file.NoSuchFileException
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ReportTestConfiguration::class)
class ReportControllerIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var reportService: ReportService

    @MockBean
    lateinit var clock: Clock

    private var headers: HttpHeaders = HttpHeaders()

    @BeforeEach
    fun setUp() {

        cleanupGroups("group1", "group2", "test", "abc123", "def456")

        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        `when`(clock.zone).thenReturn(
            ZoneId.systemDefault()
        )

        `when`(clock.instant()).thenReturn(
            Instant.parse("2021-11-20T09:00:00Z"));
    }

    @Test
    fun submitReportGetReportDisplay() {
        val report =  ObjectMother.report()
        testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        val exchange = testRestTemplate.exchange(
            "/reports", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<List<ReportDisplay>>() {})
        assertThat(exchange.body ,equalTo(listOf(ObjectMother.displayReport("Test Example", Instant.parse("2021-11-20T09:00:00Z")))))
    }

    @Test
    fun deleteReportDeletesReport() {
        val report =  ObjectMother.report()
        testRestTemplate.exchange("/reports/test", HttpMethod.POST, HttpEntity(report, headers), String::class.java )
        testRestTemplate.exchange("/reports/test", HttpMethod.DELETE, HttpEntity(report, headers), String::class.java )
        val exchange = testRestTemplate.exchange(
            "/reports", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<List<ReportDisplay>>() {})
        assertThat(exchange.body ,equalTo(listOf(ObjectMother.emptyReport())))
    }

    @Test
    fun submitReportsInDifferentGroupsGetReports() {
        val reportGroup1 =  ObjectMother.report()
        testRestTemplate.exchange("/reports/group1", HttpMethod.POST, HttpEntity(reportGroup1, headers), String::class.java )

        `when`(clock.instant()).thenReturn(
            Instant.parse("2021-11-20T09:01:00Z"));

        val reportGroup2 =  ObjectMother.report()
        testRestTemplate.exchange("/reports/group2", HttpMethod.POST, HttpEntity(reportGroup2, headers), String::class.java )

        val exchange = testRestTemplate.exchange(
            "/reports", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<List<ReportDisplay>>() {})

        assertThat(exchange.body, equalTo(listOf(ObjectMother.twoGroupsDisplay())))
    }

    @Test
    fun submitReportsInDifferentGroupsGetGroupOneReports() {
        val reportGroup1 =  ObjectMother.report("Test One")
        testRestTemplate.exchange("/reports/group1", HttpMethod.POST, HttpEntity(reportGroup1, headers), String::class.java )

        `when`(clock.instant()).thenReturn(
            Instant.parse("2021-11-20T09:01:00Z"));

        val reportGroup2 =  ObjectMother.report("Test Two")
        testRestTemplate.exchange("/reports/group2", HttpMethod.POST, HttpEntity(reportGroup2, headers), String::class.java )

        val exchange = testRestTemplate.exchange(
            "/reports?groups=group1", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<List<ReportDisplay>>() {})

        assertThat(exchange.body, equalTo(listOf(ObjectMother.groupOneOnlyDisplay())))
    }

    @Test
    fun submitReportsInDifferentGroupsGetGroupTwoReports() {
        val reportGroup1 =  ObjectMother.report("Test One")
        testRestTemplate.exchange("/reports/group1", HttpMethod.POST, HttpEntity(reportGroup1, headers), String::class.java )

        `when`(clock.instant()).thenReturn(
            Instant.parse("2021-11-20T09:01:00Z"));

        val reportGroup2 =  ObjectMother.report("Test Two")
        testRestTemplate.exchange("/reports/group2", HttpMethod.POST, HttpEntity(reportGroup2, headers), String::class.java )

        val exchange = testRestTemplate.exchange(
            "/reports?groups=group2", HttpMethod.GET, HttpEntity(null, headers), object : ParameterizedTypeReference<List<ReportDisplay>>() {})

        assertThat(exchange.body, equalTo(listOf(ObjectMother.groupTwoOnlyDisplay())))
    }

    @Test
    fun getGroupNamesGetsGroupNames() {
        val reportGroup1 =  ObjectMother.report("abc123")
        testRestTemplate.exchange("/reports/abc123", HttpMethod.POST, HttpEntity(reportGroup1, headers), String::class.java )

        `when`(clock.instant()).thenReturn(
            Instant.parse("2021-11-20T09:01:00Z"));

        val reportGroup2 =  ObjectMother.report("def456")
        testRestTemplate.exchange("/reports/def456", HttpMethod.POST, HttpEntity(reportGroup2, headers), String::class.java )

        val groups = testRestTemplate.exchange(
            "/reports/groups",
            HttpMethod.GET,
            HttpEntity(null, headers),
            object : ParameterizedTypeReference<List<String>>() {})

        assertThat(groups.body, equalTo(listOf("abc123", "def456")))
    }

    private fun cleanupGroups(vararg groups: String) {
        groups.forEach {
            try {
                reportService.delete(it)
            } catch (e: NoSuchFileException) {

            }
        }

    }

}
