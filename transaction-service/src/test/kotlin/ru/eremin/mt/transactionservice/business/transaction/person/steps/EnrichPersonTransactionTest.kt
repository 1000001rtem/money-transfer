package ru.eremin.mt.transactionservice.business.transaction.person.steps

import java.math.BigDecimal
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier
import ru.eremin.mt.common.error.MoneyTransferException
import ru.eremin.mt.transactionservice.fabric.accountDto
import ru.eremin.mt.transactionservice.fabric.personTransactionContext
import ru.eremin.mt.transactionservice.fabric.personTransactionInfo
import ru.eremin.mt.transactionservice.output.client.AccountsClient
import ru.eremin.mt.transactionservice.util.error.Errors

class EnrichPersonTransactionTest {

    lateinit var subj: EnrichPersonTransaction

    lateinit var accountsClient: AccountsClient

    @BeforeEach
    fun setUp() {
        accountsClient = mock()
        subj = EnrichPersonTransaction(accountsClient)
    }

    @Test
    fun `should enrich context`() {
        val account = accountDto()
        val info = personTransactionInfo(fromAccount = account.id)
        val context = personTransactionContext(info = info)

        whenever(accountsClient.findById(any())).thenReturn(
            account.toMono()
        )

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.transaction.from!!.account).isEqualTo(account.id)
                assertThat(it.transaction.from!!.currency).isEqualTo(account.currency)
                true
            }
            .verifyComplete()

        verify(accountsClient, times(1)).findById(any())
    }

    @Test
    fun `should throw exception if wrong currency in account`() {
        val account = accountDto(currency = Currency.getInstance("EUR"))
        val info = personTransactionInfo(fromAccount = account.id)
        val context = personTransactionContext(info = info)

        whenever(accountsClient.findById(any())).thenReturn(
            account.toMono()
        )

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectErrorMatches {
                assertThat(it).isInstanceOf(MoneyTransferException::class.java)
                assertThat((it as MoneyTransferException).code).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.code)
                assertThat(it.message).isEqualTo(Errors.DIFFERENT_CURRENCY_ERROR.message)
                true
            }
            .verify()

        verify(accountsClient, times(1)).findById(any())
    }

    @Test
    fun `should throw exception if not enough funds on balance`() {
        val account = accountDto(balance = BigDecimal.TEN)
        val info = personTransactionInfo(fromAccount = account.id)
        val context = personTransactionContext(info = info)

        whenever(accountsClient.findById(any())).thenReturn(
            account.toMono()
        )

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectErrorMatches {
                assertThat(it).isInstanceOf(MoneyTransferException::class.java)
                assertThat((it as MoneyTransferException).code).isEqualTo(
                    Errors.INSUFFICIENT_FUNDS.formatMessage(
                        context.getTransactionId()
                    ).code
                )
                assertThat(it.message).isEqualTo(Errors.INSUFFICIENT_FUNDS.formatMessage(context.getTransactionId()).message)
                true
            }
            .verify()

        verify(accountsClient, times(1)).findById(any())
    }
}