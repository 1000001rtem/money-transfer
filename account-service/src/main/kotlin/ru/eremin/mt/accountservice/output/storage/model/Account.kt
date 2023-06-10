package ru.eremin.mt.accountservice.output.storage.model

import java.math.BigDecimal
import java.util.*
import org.springframework.data.relational.core.mapping.Table


@Table(name = "accounts")
class Account(
    val currency: Currency,
    val balance: BigDecimal,
    val ownerId: UUID,
) : Entity<UUID>(UUID.randomUUID())
