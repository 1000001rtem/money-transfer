package ru.eremin.mt.transactionservice.input.rest

import java.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.response.MtDataResponse
import ru.eremin.mt.common.model.response.MtResponse
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.service.TransactionService

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun make(
        @RequestBody request: TransactionInfo,
        @RequestHeader("userId") userId: String
    ): Mono<MtResponse> = transactionService.makeTransaction(request, userId)
        .map { MtDataResponse(it) }

    @GetMapping
    fun findById(@RequestParam("id") id: String): Mono<MtResponse> =
        transactionService.findTransaction(id)
            .map { MtDataResponse(it) }

    @GetMapping
    fun findByUserId(@RequestParam("userId") userId: String): Flux<MtResponse> =
        transactionService.findTransactionsByUserId(userId)
            .map { MtDataResponse(it) }
}