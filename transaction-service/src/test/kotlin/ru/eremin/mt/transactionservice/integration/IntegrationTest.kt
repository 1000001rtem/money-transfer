package ru.eremin.mt.transactionservice.integration

import java.time.Duration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class IntegrationTest {
    companion object {
        @JvmStatic
        @Container
        var container = MongoDBContainer(DockerImageName.parse("mongo:5"))
            .waitingFor(
                Wait.forLogMessage(".*waiting for connections.*", 1)
                    .withStartupTimeout(Duration.ofSeconds(1200))
            )

        @JvmStatic
        @DynamicPropertySource
        fun mongoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { container.replicaSetUrl }
        }
    }
}