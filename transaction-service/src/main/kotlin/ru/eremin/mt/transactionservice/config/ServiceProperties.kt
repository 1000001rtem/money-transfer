package ru.eremin.mt.transactionservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "service")
class ServiceProperties {
    lateinit var eventBindings: Map<String, String>
}