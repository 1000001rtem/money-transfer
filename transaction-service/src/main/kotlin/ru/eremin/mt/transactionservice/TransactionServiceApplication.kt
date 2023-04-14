package ru.eremin.mt.transactionservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class TransactionServiceApplication

fun main(args: Array<String>) {
    runApplication<TransactionServiceApplication>(*args)
}
