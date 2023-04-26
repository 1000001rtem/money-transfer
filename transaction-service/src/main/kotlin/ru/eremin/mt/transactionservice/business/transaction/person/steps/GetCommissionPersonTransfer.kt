package ru.eremin.mt.transactionservice.business.transaction.person.steps

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.eremin.mt.transactionservice.business.process.Step
import ru.eremin.mt.transactionservice.business.service.CommissionService
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext

@Component
class GetCommissionPersonTransfer(
    private val commissionService: CommissionService
) : Step<PersonTransactionContext> {
    override fun isApply(context: PersonTransactionContext): Boolean = true

    override fun apply(context: PersonTransactionContext): Mono<PersonTransactionContext> =
        commissionService.getCommission(context.transactionInfo.transactionType)
            .map {
                context.copy(
                    transaction = context.transaction.copy(
                        commission = it
                    )
                )
            }
            .subscribeOn(Schedulers.boundedElastic())
}