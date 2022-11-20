package com.alwa.testhub.domain

import java.time.Instant

data class TestResult (
    val name: String,
    val reportTime: Instant,
    val success: Boolean,
    val screenShot: ScreenShot?,
    val group: String,
    val messages: List<String>,
    val properties: List<Property>
)

data class Property(
    val name: String,
    val value: String
)