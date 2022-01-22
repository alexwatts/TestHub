package com.alwa.testhub.domain

import java.time.Instant

interface Report {
    fun getTime(): Instant
    fun getTestResults(): List<TestResult>
}
