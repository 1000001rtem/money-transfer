package ru.eremin.mt.accountservice.output.storage.model

import java.math.BigDecimal
import java.util.*
import org.springframework.data.relational.core.mapping.Table

@Table(name = "reservations")
class Reservation(
    val accountId: UUID,
    val amount: BigDecimal,
    val transactionId: String,
) : Entity<UUID>(UUID.randomUUID())