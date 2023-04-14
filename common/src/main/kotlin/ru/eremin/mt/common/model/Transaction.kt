package ru.eremin.mt.common.model

import java.math.BigDecimal
import java.util.*

class Transaction(
    val id: String,
    val from: SideInfo?,
    val to: SideInfo?,
    val commission: BigDecimal,
    val conversationRate: BigDecimal?,
    val status: TransactionStatus
)

class SideInfo(
    val currency: Currency,
    val account: String,
    val amount: BigDecimal
)