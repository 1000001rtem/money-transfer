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
import ru.eremin.mt.common.model.domain.TransactionType
import ru.eremin.mt.transactionservice.business.service.CommissionService
import ru.eremin.mt.transactionservice.fabric.personTransactionContext

class GetCommissionPersonTransferTest {

    lateinit var subj: GetCommissionPersonTransfer

    lateinit var commissionService: CommissionService

    @BeforeEach
    fun setUp() {
        commissionService = mock()
        subj = GetCommissionPersonTransfer(commissionService)
    }

    @Test
    fun `should set commission to context`() {
        val context = personTransactionContext()
        val commission = BigDecimal(1.5)

        whenever(commissionService.getCommission(any())).thenReturn(BigDecimal("1.5").toMono())

        subj.apply(context)
            .`as` { StepVerifier.create(it) }
            .expectNextMatches {
                assertThat(it.transaction.commission).isEqualTo(commission)
                true
            }
            .verifyComplete()

        verify(commissionService, times(1)).getCommission(argThat {
            assertThat(this).isEqualTo(TransactionType.PERSON)
            true
        })
    }
}