package com.alwa.testhub.domain

import java.time.Instant

data class ReportData(
    val time: Instant,
    val report: String,
    val group: String)
