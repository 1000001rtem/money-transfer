package ru.eremin.mt.transactionservice.business.transaction.person.steps

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.eremin.mt.common.model.request.OperationRequest
import ru.eremin.mt.transactionservice.business.process.Step
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.output.client.ReservationClient
import ru.eremin.mt.transactionservice.util.error.Errors

@Component
class ReservePersonTransfer(
    private val reservationClient: ReservationClient
) : Step<PersonTransactionContext> {
    override fun isApply(context: PersonTransactionContext): Boolean = true

    override fun apply(context: PersonTransactionContext): Mono<PersonTransactionContext> =
        reservationClient.reserve(
            OperationRequest(
                context.transaction.from?.account!!,
                context.transaction.from.amount.multiply(context.transaction.commission),
                context.transaction.from.currency.currencyCode,
                context.getTransactionId()
            )
        )
            .flatMap {
                if (it.isSuccess) {
                    log.trace("Funds reserved for transaction ${context.transaction.id}")
                    context.toMono()
                } else Mono.error(Errors.INSUFFICIENT_FUNDS.replaceMessages(message = it.comment).asException())
            }

    companion object {
        val log = LoggerFactory.getLogger(this::class.java)
    }
}