package ru.eremin.mt.accountservice.input

import java.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.accountservice.business.service.AccountService
import ru.eremin.mt.accountservice.input.dto.OperationRequest
import ru.eremin.mt.common.model.domain.AccountDto

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/find-by-user")
    fun findByUserId(@RequestParam("userId") userId: UUID): Flux<AccountDto> =
        accountService.findAccountsByUser(userId)

    @GetMapping
    fun findById(@RequestParam("id") id: UUID): Mono<AccountDto> =
        accountService.findAccountsById(id)

    @PostMapping("debit")
    fun debitFunds(@RequestBody request: OperationRequest) =
        accountService.debitFunds(
            accountId = request.accountId,
            amount = request.amount,
            currency = Currency.getInstance(request.currencyCode)
        )

    @PostMapping("add")
    fun addFunds(@RequestBody request: OperationRequest) =
        accountService.addFunds(
            accountId = request.accountId,
            amount = request.amount,
            currency = Currency.getInstance(request.currencyCode)
        )
}