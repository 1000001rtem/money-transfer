package ru.eremin.mt.accountservice.business.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class ReservationDto(
    val id: UUID,
    val accountId: UUID,
    val amount: BigDecimal,
    val createdAt: LocalDateTime,
    var updateAt: LocalDateTime
)