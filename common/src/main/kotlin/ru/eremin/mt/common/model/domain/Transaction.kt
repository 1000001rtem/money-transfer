package ru.eremin.mt.common.model.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Transaction(
    val id: String,
    val from: SideInfo? = null,
    val to: SideInfo? = null,
    val commission: BigDecimal? = null,
    val conversationRate: BigDecimal? = null,
    val status: TransactionStatus,
    val userId: String,
    val createAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val comment: String? = null
)

data class SideInfo(
    val currency: Currency,
    val account: UUID,
    val amount: BigDecimal
)