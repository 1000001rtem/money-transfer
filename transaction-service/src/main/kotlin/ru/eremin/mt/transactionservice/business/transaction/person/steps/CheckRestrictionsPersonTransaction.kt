package ru.eremin.mt.transactionservice.business.transaction.person.steps

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.eremin.mt.transactionservice.business.process.Step
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext

@Component
class CheckRestrictionsPersonTransaction : Step<PersonTransactionContext> {
    override fun isApply(context: PersonTransactionContext): Boolean = false

    override fun apply(context: PersonTransactionContext): Mono<PersonTransactionContext> {
        return context.toMono()
    }
}