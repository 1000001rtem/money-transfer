package ru.eremin.mt.transactionservice.business.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.domain.Transaction
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument

interface TransactionService {

    fun makeTransaction(info: TransactionInfo, userId: String): Mono<Transaction>

    fun findTransaction(id: String): Mono<Transaction>

    fun findTransactionsByUserId(userId: String): Flux<Transaction>

    fun updateTransactionStatus(
        transactionId: String,
        status: TransactionStatus,
        comment: String? = null
    ): Mono<TransactionDocument>

}