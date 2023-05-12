package ru.eremin.mt.transactionservice.business.service.impl

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.switchIfEmpty
import ru.eremin.mt.common.model.domain.Transaction
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.process.Context
import ru.eremin.mt.transactionservice.business.process.Process
import ru.eremin.mt.transactionservice.business.service.TransactionService
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository
import ru.eremin.mt.transactionservice.util.error.Errors.NOT_IMPLEMENTED_TRANSACTION_TYPE
import ru.eremin.mt.transactionservice.util.error.Errors.TRANSACTION_NOT_FOUND
import ru.eremin.mt.transactionservice.util.mapper.toDto

@Service
class TransactionServiceImpl(
    processList: List<Process<*, Transaction>>,
    private val transactionRepository: TransactionRepository
) : TransactionService {

    private val processes: Map<String, Process<*, Transaction>> = processList.associateBy { it.processName }
    override fun makeTransaction(info: TransactionInfo, userId: String): Mono<Transaction> =
        processes["transaction/${info.transactionType.toString().lowercase()}"]?.let {
            it as Process<Context, Transaction>
            val context: Context = PersonTransactionContext(info, userId)
            it.process(context)
        } ?: throw NOT_IMPLEMENTED_TRANSACTION_TYPE.asException()


    override fun findTransaction(id: String): Mono<Transaction> =
        transactionRepository.findById(id)
            .map { it.toDto() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun findTransactionsByUserId(userId: String): Flux<Transaction> =
        transactionRepository.findByUserId(userId)
            .map { it.toDto() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun updateTransactionStatus(
        transactionId: String,
        status: TransactionStatus,
        comment: String?
    ): Mono<TransactionDocument> =
        transactionRepository.findById(transactionId)
            .flatMap {
                transactionRepository.save(it.copy(status = status, comment = comment, updatedAt = LocalDateTime.now()))
            }
            .switchIfEmpty { Mono.error(TRANSACTION_NOT_FOUND.formatMessage(transactionId).asException()) }
}