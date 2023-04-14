package ru.eremin.mt.accountservice.input.dto

import java.math.BigDecimal
import java.util.*

data class OperationRequest(
    val accountId: UUID,
    val amount: BigDecimal,
    val currencyCode: String
)