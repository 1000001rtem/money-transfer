package ru.eremin.mt.accountservice.business.service.impl

import java.math.BigDecimal
import java.util.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.accountservice.business.service.AccountService
import ru.eremin.mt.common.model.domain.AccountDto

@Service
class AccountServiceImpl : AccountService {
    override fun findAccountsByUser(userId: UUID): Flux<AccountDto> {
        TODO("Not yet implemented")
    }

    override fun findAccountsById(id: UUID): Mono<AccountDto> {
        TODO("Not yet implemented")
    }

    override fun debitFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun addFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}