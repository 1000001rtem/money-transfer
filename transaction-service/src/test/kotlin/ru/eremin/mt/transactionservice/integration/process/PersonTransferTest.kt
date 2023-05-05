package ru.eremin.mt.transactionservice.integration.process

import com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigDecimal
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import ru.eremin.mt.common.event.TransactionCreated
import ru.eremin.mt.common.event.TransactionError
import ru.eremin.mt.common.event.TransactionRejected
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.common.model.response.ReservationResult
import ru.eremin.mt.transactionservice.fabric.TEST_USER
import ru.eremin.mt.transactionservice.fabric.accountDto
import ru.eremin.mt.transactionservice.fabric.personTransactionInfo
import ru.eremin.mt.transactionservice.integration.IntegrationTest
import ru.eremin.mt.transactionservice.output.client.AccountsClient
import ru.eremin.mt.transactionservice.output.client.ReservationClient
import ru.eremin.mt.transactionservice.output.messaging.EventSender
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository
import ru.eremin.mt.transactionservice.util.error.Errors

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@DirtiesContext(classMode= DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonTransferTest : IntegrationTest() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var accountsClient: AccountsClient

    @MockBean
    lateinit var reservationClient: ReservationClient

    @MockBean
    lateinit var eventSender: EventSender

    @AfterEach
    fun clear() {
        transactionRepository.deleteAll().block()
    }

    @Test
    fun `should process transaction`() {
        val account = accountDto()
        val info = personTransactionInfo(fromAccount = account.id)

        whenever(accountsClient.findById(any())).thenReturn(account.toMono())
        whenever(reservationClient.reserve(any())).thenReturn(ReservationResult(isSuccess = true).toMono())
        doNothing().whenever(eventSender).send(any())

        var transactionId: String? = null

        webTestClient.post()
            .uri("/api/v1/transaction")
            .header("userId", TEST_USER)
            .contentType(APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(
                    mapper.writeValueAsString(info)
                )
            )
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.data.id").value<String> {
                assertThat(it).isNotNull()
                transactionId = it
            }

        verify(eventSender).send(argThat {
            assertThat(this).isInstanceOf(TransactionCreated::class.java)
            val transaction = (this as TransactionCreated).transaction
            assertThat(transaction).isNotNull
            assertThat(transaction.id).isEqualTo(transactionId)
            assertThat(transaction.commission).isNotNull
            StepVerifier.create(
                transactionRepository.findById(transaction.id)
            )
                .expectNextMatches {
                    assertThat(it).isNotNull
                    assertThat(it.commission).isNotNull
                    assertThat(it.status).isEqualTo(TransactionStatus.APPROVE)
                    true
                }
                .verifyComplete()
            true
        })
    }

    @Test
    fun `should send finalize process with error`() {
        val account = accountDto(currency = Currency.getInstance("EUR"))
        val info = personTransactionInfo(fromAccount = account.id)

        whenever(accountsClient.findById(any())).thenReturn(account.toMono())
        doNothing().whenever(eventSender).send(any())

        webTestClient.post()
            .uri("/api/v1/transaction")
            .header("userId", TEST_USER)
            .contentType(APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(
                    mapper.writeValueAsString(info)
                )
            )
            .exchange()
            .expectStatus().is5xxServerError

        verify(eventSender).send(argThat {
            assertThat(this).isInstanceOf(TransactionError::class.java)
            val event = this as TransactionError
            assertThat(event.transactionId).isNotNull
            assertThat(event.comment).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.message)
            StepVerifier.create(
                transactionRepository.findById(event.transactionId)
            )
                .expectNextMatches {
                    assertThat(it).isNotNull
                    assertThat(it.status).isEqualTo(TransactionStatus.ERROR)
                    assertThat(it.comment).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.message)
                    true
                }
                .verifyComplete()
            true
        })
    }

    @Test
    fun `should send finalize process with reject`() {
        val account = accountDto(balance = BigDecimal.TEN)
        val info = personTransactionInfo(fromAccount = account.id)

        whenever(accountsClient.findById(any())).thenReturn(account.toMono())
        doNothing().whenever(eventSender).send(any())

        webTestClient.post()
            .uri("/api/v1/transaction")
            .header("userId", TEST_USER)
            .contentType(APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(
                    mapper.writeValueAsString(info)
                )
            )
            .exchange()
            .expectStatus().is5xxServerError

        verify(eventSender).send(argThat {
            assertThat(this).isInstanceOf(TransactionRejected::class.java)
            val event = this as TransactionRejected
            assertThat(event.transactionId).isNotNull
            assertThat(event.comment).isEqualTo(Errors.INSUFFICIENT_FUNDS.formatMessage(transactionId).message)
            StepVerifier.create(
                transactionRepository.findById(event.transactionId)
            )
                .expectNextMatches {
                    assertThat(it).isNotNull
                    assertThat(it.status).isEqualTo(TransactionStatus.DECLINE)
                    assertThat(it.comment).isEqualTo(Errors.INSUFFICIENT_FUNDS.formatMessage(transactionId).message)
                    true
                }
                .verifyComplete()
            true
        })
    }
}