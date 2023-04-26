package ru.eremin.mt.accountservice.output.storage.model

import java.math.BigDecimal
import java.util.*
import org.springframework.data.annotation.Id

class Reservation(
    @Id
    val id: UUID = UUID.randomUUID(),
    val accountId: UUID,
    val amount: BigDecimal,
    val transactionId: String,
) : Entity()