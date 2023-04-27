package ru.eremin.mt.transactionservice.business.transaction.person

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.eremin.mt.common.error.MoneyTransferException
import ru.eremin.mt.common.event.TransactionCreated
import ru.eremin.mt.common.event.TransactionError
import ru.eremin.mt.common.event.TransactionRejected
import ru.eremin.mt.common.model.domain.Transaction
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.business.process.Finalizer
import ru.eremin.mt.transactionservice.output.messaging.EventSender
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository
import ru.eremin.mt.transactionservice.util.error.Errors
import ru.eremin.mt.transactionservice.util.error.Errors.INSUFFICIENT_FUNDS
import ru.eremin.mt.transactionservice.util.mapper.toDto

@Component
class PersonTransactionFinalizer(
    private val transactionRepository: TransactionRepository,
    private val eventSender: EventSender
) : Finalizer<PersonTransactionContext, Transaction> {
    override fun finalize(context: PersonTransactionContext): Mono<Transaction> =
        context.transaction.toMono()
            .flatMap {
                it.copy(status = TransactionStatus.APPROVE)
                    .let { transaction ->
                        transactionRepository.save(transaction)
                    }
            }
            .map {
                it.toDto()
                    .also { transaction ->
                        eventSender.send(
                            TransactionCreated(transaction)
                        )
                    }
            }

    override fun finalizeError(error: Throwable, id: String) {
        if (error is MoneyTransferException) {
            when (Errors.valueOf(error.code)) {
                // decline
                INSUFFICIENT_FUNDS -> {
                    handleDecline(error, id)
                }
                //error
                else -> {
                    handleError(error, id)
                }
            }
        } else {
            handleError(error, id)
        }
    }

    private fun handleDecline(error: Throwable, transactionId: String) =
        transactionRepository.findById(transactionId)
            .map {
                it.copy(
                    status = TransactionStatus.DECLINE,
                    comment = error.message
                )
            }
            .flatMap {
                transactionRepository.save(it)
            }
            .map { eventSender.send(TransactionRejected(it.id, it.comment)) }
            .publishOn(Schedulers.boundedElastic())
            .subscribe()

    private fun handleError(error: Throwable, transactionId: String) =
        transactionRepository.findById(transactionId)
            .map {
                it.copy(
                    status = TransactionStatus.ERROR,
                    comment = error.message
                )
            }
            .flatMap {
                transactionRepository.save(it)
            }
            .map { eventSender.send(TransactionError(it.id, it.comment)) }
            .publishOn(Schedulers.boundedElastic())
            .subscribe()
}