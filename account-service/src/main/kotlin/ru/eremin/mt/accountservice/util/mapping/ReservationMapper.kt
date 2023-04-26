package ru.eremin.mt.accountservice.util.mapping

import ru.eremin.mt.accountservice.output.storage.model.Reservation
import ru.eremin.mt.common.model.domain.ReservationDto

fun Reservation.toDto() = ReservationDto(
    id = this.id,
    accountId = this.accountId,
    amount = this.amount,
    createdAt = this.createdAt,
    transactionId = this.transactionId,
    updateAt = this.updateAt
)