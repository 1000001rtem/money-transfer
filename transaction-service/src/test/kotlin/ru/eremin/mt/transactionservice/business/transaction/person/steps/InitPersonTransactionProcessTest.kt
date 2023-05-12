package ru.eremin.mt.transactionservice.business.transaction.person.steps

import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import ru.eremin.mt.common.error.MoneyTransferException
import ru.eremin.mt.transactionservice.fabric.personTransactionContext
import ru.eremin.mt.transactionservice.fabric.personTransactionInfo
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository
import ru.eremin.mt.transactionservice.util.error.Errors

class InitPersonTransactionProcessTest {

    lateinit var subj: InitPersonTransactionProcess

    lateinit var repository: TransactionRepository

    @BeforeEach
    fun setUp() {
        repository = mock()
        subj = InitPersonTransactionProcess(repository)
    }


    @Test
    fun `should save new transaction`() {
        val context = personTransactionContext()

        whenever(repository.save(any())).thenReturn(context.transaction.toMono())

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.getTransactionId()).isEqualTo(context.getTransactionId())
                true
            }
            .verifyComplete()
        verify(repository, times(1)).save(any())
    }

    @Test
    fun `should throw exception when currency not equal`() {
        val transactionInfo = personTransactionInfo(
            toCurrency = Currency.getInstance("EUR")
        )
        val context = personTransactionContext(info = transactionInfo)

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectErrorMatches {
                assertThat(it).isInstanceOf(MoneyTransferException::class.java)
                assertThat((it as MoneyTransferException).code).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.code)
                assertThat(it.message).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.message)
                true
            }
            .verify()

        verify(repository, never()).save(any())
    }
}