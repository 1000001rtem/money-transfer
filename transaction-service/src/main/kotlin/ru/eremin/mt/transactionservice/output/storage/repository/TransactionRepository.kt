package ru.eremin.mt.transactionservice.output.storage.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import ru.eremin.mt.transactionservice.output.storage.model.Transaction

interface TransactionRepository : ReactiveMongoRepository<Transaction, String>