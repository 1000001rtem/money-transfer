package ru.eremin.mt.transactionservice.business.service

import java.util.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.output.storage.model.Transaction

interface TransactionService {

    fun makeTransaction(info: TransactionInfo): Mono<String>

    fun findTransaction(id: String): Mono<Transaction>

    fun findTransactionsByUserId(userId: UUID): Flux<Transaction>

}