package ru.eremin.mt.transactionservice.input

import java.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.service.TransactionService
import ru.eremin.mt.transactionservice.output.storage.model.Transaction

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun make(@RequestBody request: TransactionInfo): Mono<String> = transactionService.makeTransaction(request)

    @GetMapping
    fun findById(@RequestParam("id") id: String): Mono<Transaction> = transactionService.findTransaction(id)

    @GetMapping
    fun findByUserId(@RequestParam("userId") userId: UUID): Flux<Transaction> =
        transactionService.findTransactionsByUserId(userId)
}