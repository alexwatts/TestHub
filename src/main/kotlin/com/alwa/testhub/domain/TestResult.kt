package com.alwa.testhub.domain

import java.time.Instant

data class TestResult(val name: String, val reportTime: Instant, val success: Boolean, val screenShot: ScreenShot?)
