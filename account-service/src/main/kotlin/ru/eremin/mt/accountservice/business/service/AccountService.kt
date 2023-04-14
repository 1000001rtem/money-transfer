package ru.eremin.mt.accountservice.business.service

import java.math.BigDecimal
import java.util.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.accountservice.business.dto.AccountDto

interface AccountService {

    fun findAccountsByUser(userId: UUID): Flux<AccountDto>

    fun findAccountsById(id: UUID): Mono<AccountDto>

    fun debitFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean>

    fun addFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean>

}