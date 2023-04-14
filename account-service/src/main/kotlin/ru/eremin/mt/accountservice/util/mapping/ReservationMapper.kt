package ru.eremin.mt.accountservice.util.mapping

import ru.eremin.mt.accountservice.business.dto.ReservationDto
import ru.eremin.mt.accountservice.output.storage.model.Reservation

fun Reservation.toDto() = ReservationDto(
    id = this.id,
    accountId = this.accountId,
    amount = this.amount,
    createdAt = this.createdAt,
    updateAt = this.updateAt
)