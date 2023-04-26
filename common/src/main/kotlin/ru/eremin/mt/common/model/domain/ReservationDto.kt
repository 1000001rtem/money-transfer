package ru.eremin.mt.common.model.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class ReservationDto(
    val id: UUID,
    val accountId: UUID,
    val amount: BigDecimal,
    val transactionId: String,
    val createdAt: LocalDateTime,
    var updateAt: LocalDateTime
)