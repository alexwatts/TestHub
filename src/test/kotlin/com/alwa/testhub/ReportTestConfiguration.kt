package com.alwa.testhub

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Configuration
open class ReportTestConfiguration {

    @Primary
    @Bean
    open fun stoppedClock(): Clock {
        return Clock.fixed(
            Instant.parse("2021-11-20T09:00:00Z"),
            ZoneId.systemDefault()
        )
    }
}
