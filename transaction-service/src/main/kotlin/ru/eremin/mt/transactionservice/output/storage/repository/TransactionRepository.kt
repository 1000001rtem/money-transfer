package ru.eremin.mt.transactionservice.output.storage.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument

interface TransactionRepository : ReactiveMongoRepository<TransactionDocument, String> {
    fun findByUserId(userId: String): Flux<TransactionDocument>
}