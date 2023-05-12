package ru.eremin.mt.transactionservice.business.transaction.person.steps

import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import ru.eremin.mt.common.error.MoneyTransferException
import ru.eremin.mt.common.model.response.ReservationResult
import ru.eremin.mt.transactionservice.fabric.fullPersonTransaction
import ru.eremin.mt.transactionservice.fabric.personTransactionContext
import ru.eremin.mt.transactionservice.output.client.ReservationClient
import ru.eremin.mt.transactionservice.util.error.Errors

class ReservePersonTransferTest {

    lateinit var subj: ReservePersonTransfer

    lateinit var reservationClient: ReservationClient

    @BeforeEach
    fun setUp() {
        reservationClient = mock()
        subj = ReservePersonTransfer(reservationClient)
    }

    @Test
    fun `should send amount with commission`() {
        val context = personTransactionContext().copy(
            transaction = fullPersonTransaction()
        )

        whenever(reservationClient.reserve(any())).thenReturn(
            ReservationResult(isSuccess = true).toMono()
        )

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectNextCount(1)
            .verifyComplete()

        verify(reservationClient, times(1)).reserve(argThat {
            assertThat(this.amount).isEqualTo(BigDecimal("42.60"))
            true
        })
    }

    @Test
    fun `should throw exception if not enough funds`() {
        val context = personTransactionContext().copy(
            transaction = fullPersonTransaction()
        )

        whenever(reservationClient.reserve(any())).thenReturn(
            ReservationResult(isSuccess = false, comment = "not enough money").toMono()
        )

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectErrorMatches {
                assertThat(it).isInstanceOf(MoneyTransferException::class.java)
                assertThat((it as MoneyTransferException).code).isEqualTo(Errors.INSUFFICIENT_FUNDS.code)
                assertThat(it.message).isEqualTo("not enough money")
                true
            }
            .verify()

        verify(reservationClient, times(1)).reserve(any())
    }
}