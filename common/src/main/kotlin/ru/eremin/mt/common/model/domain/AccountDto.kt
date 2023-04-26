package ru.eremin.mt.common.model.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class AccountDto(
    val id: UUID,
    val currency: Currency,
    val balance: BigDecimal,
    val ownerId: String,
    val createdAt: LocalDateTime,
    var updateAt: LocalDateTime
)
