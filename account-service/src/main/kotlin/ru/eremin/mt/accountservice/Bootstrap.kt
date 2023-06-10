package ru.eremin.mt.accountservice

import java.math.BigDecimal
import java.util.*
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.eremin.mt.accountservice.output.storage.model.Account
import ru.eremin.mt.accountservice.output.storage.model.User
import ru.eremin.mt.accountservice.output.storage.repository.AccountRepository
import ru.eremin.mt.accountservice.output.storage.repository.UserRepository

@Component
class Bootstrap(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        if (userRepository.findAll().collectList().block()?.isNotEmpty() == true) return
        val stan = User(firstName = "Stan", lastName = "Marsch", email = "stan@test.com").also { it.isNew = true }
        val kyle = User(firstName = "Kyle", lastName = "Broflovski", email = "kyle@test.com").also { it.isNew = true }

        userRepository.saveAll(
            listOf(stan, kyle)
        ).subscribe()

        accountRepository.saveAll(
            listOf(
                Account(
                    currency = Currency.getInstance("USD"),
                    balance = BigDecimal.valueOf(42L),
                    ownerId = stan.id
                ).also { it.isNew = true },
                Account(
                    currency = Currency.getInstance("EUR"),
                    balance = BigDecimal.valueOf(42L),
                    ownerId = stan.id
                ).also { it.isNew = true },
                Account(
                    currency = Currency.getInstance("EUR"),
                    balance = BigDecimal.valueOf(42L),
                    ownerId = kyle.id
                ).also { it.isNew = true },
            )
        ).subscribe()
    }
}