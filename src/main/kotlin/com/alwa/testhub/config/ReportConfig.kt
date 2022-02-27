package com.alwa.testhub.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
open class ReportConfig {
    @Bean
    open fun clock(): Clock {
        return Clock.systemDefaultZone()
    }
}
