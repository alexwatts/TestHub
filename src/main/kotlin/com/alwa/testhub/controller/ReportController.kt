package com.alwa.testhub.controller

import com.alwa.testhub.domain.ReportDisplay
import com.alwa.testhub.service.ReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/reports"])
class ReportController(private val reportService: ReportService) {

    @RequestMapping(
        value = ["{group}"],
        consumes = ["application/json"],
        produces = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun createReport(
        @PathVariable("group") group: String,
        @RequestBody reportData: String) =
        reportService.createReport(reportData, group)

    @ResponseBody
    @RequestMapping(
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getReportDisplay(
        @RequestParam(name = "groups", required = false) groups: Array<String>?):
            ResponseEntity<List<ReportDisplay>> =
        ResponseEntity(
            reportService.getReports(
                Parameters.groupsOrDefault(groups)
            ),
            HttpStatus.OK
        )

    @RequestMapping(
        value = ["{group}"],
        method = [RequestMethod.DELETE]
    )
    fun deleteGroup(@PathVariable("group") group: String) =
        reportService.delete(group)

    @ResponseBody
    @RequestMapping(
        value = ["/groups"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getGroups(): ResponseEntity<List<String>> =
        ResponseEntity(reportService.getGroups(), HttpStatus.OK)

}