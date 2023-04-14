package ru.eremin.mt.accountservice.output.storage.model

import java.math.BigDecimal
import java.util.*
import org.springframework.data.annotation.Id

class Account(
    @Id
    val id: UUID = UUID.randomUUID(),
    val currency: Currency,
    val balance: BigDecimal,
    val owner: User
) : Entity()
