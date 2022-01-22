package com.alwa.testhub.controller

import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.service.ReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/reports"])
class ReportController(val reportService: ReportService) {

    @RequestMapping(
        value = ["{partition}"],
        consumes = ["application/json"],
        produces = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun createReport(
        @PathVariable("partition") partition: String,
        @RequestBody reportData: String) =
        reportService.createReport(reportData, partition)

    @ResponseBody
    @RequestMapping(
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getReportDisplay(): ResponseEntity<ReportDisplay> =
        ResponseEntity(reportService.getReports(), HttpStatus.OK)

    @RequestMapping(
        value = ["{partition}"],
        method = [RequestMethod.DELETE]
    )
    fun deletePartition(@PathVariable("partition") partition: String) =
        reportService.delete(partition)
}

