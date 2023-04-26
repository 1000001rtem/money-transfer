package ru.eremin.mt.common.model.request

import java.math.BigDecimal
import java.util.*

data class OperationRequest(
    val accountId: UUID,
    val amount: BigDecimal,
    val currencyCode: String,
    val transactionId: String,
)