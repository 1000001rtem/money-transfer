package ru.eremin.mt.transactionservice.integration.messaging

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier
import ru.eremin.mt.common.event.TransactionError
import ru.eremin.mt.common.event.TransactionRejected
import ru.eremin.mt.common.event.TransactionSent
import ru.eremin.mt.common.event.TransactionSucceed
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.fabric.fullPersonTransaction
import ru.eremin.mt.transactionservice.integration.KafkaTest
import ru.eremin.mt.transactionservice.output.messaging.EventSender
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository

class ListenersTest : KafkaTest() {

    @Autowired
    lateinit var sender: EventSender

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    companion object {
        @BeforeAll
        @JvmStatic
        fun createTopic() {
            kafkaContainer.execInContainer("kafka /bin/kafka-topics --create --topic mt.events.pub --bootstrap-server localhost:9092")
        }
    }

    @Test
    fun `should receive transactionSent event`() {
        val transaction = fullPersonTransaction()

        transactionRepository.save(transaction).block()

        val event = TransactionSent(transaction.id)
        sender.send(event)

        Thread.sleep(2000)
        transactionRepository.findById(transaction.id)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.status).isEqualTo(TransactionStatus.SENT)
                true
            }
            .verifyComplete()
    }

    @Test
    fun `should receive transactionSucceed event`() {
        val transaction = fullPersonTransaction()

        transactionRepository.save(transaction).block()

        val event = TransactionSucceed(transaction.id)
        sender.send(event)

        Thread.sleep(2000)
        transactionRepository.findById(transaction.id)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.status).isEqualTo(TransactionStatus.DONE)
                true
            }
            .verifyComplete()
    }

    @Test
    fun `should receive transactionReject event`() {
        val transaction = fullPersonTransaction()

        transactionRepository.save(transaction).block()

        val event = TransactionRejected(transaction.id, "42")
        sender.send(event)

        Thread.sleep(2000)
        transactionRepository.findById(transaction.id)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.status).isEqualTo(TransactionStatus.DECLINE)
                assertThat(it.comment).isEqualTo("42")
                true
            }
            .verifyComplete()
    }

    @Test
    fun `should receive transactionError event`() {
        val transaction = fullPersonTransaction()

        transactionRepository.save(transaction).block()

        val event = TransactionError(transaction.id, "42")
        sender.send(event)

        Thread.sleep(2000)
        transactionRepository.findById(transaction.id)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.status).isEqualTo(TransactionStatus.ERROR)
                assertThat(it.comment).isEqualTo("42")
                true
            }
            .verifyComplete()
    }
}