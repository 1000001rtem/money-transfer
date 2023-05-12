package ru.eremin.mt.transactionservice.integration;

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName

@Testcontainers
class KafkaTest : IntegrationTest() {
    companion object {
        @JvmStatic
        @Container
        val kafkaContainer = KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:6.2.1")
        )

        @JvmStatic
        @DynamicPropertySource
        fun kafkaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.cloud.stream.kafka.binder.brokers") { kafkaContainer.bootstrapServers }
            registry.add("spring.kafka.bootstrap-servers") { kafkaContainer.bootstrapServers }
            registry.add("spring.kafka.producer.value-serializer") { "org.springframework.kafka.support.serializer.JsonSerializer" }
            registry.add("spring.cloud.stream.kafka.binder.configuration.value.serializer") { "org.springframework.kafka.support.serializer.JsonSerializer" }
        }

    }
}
