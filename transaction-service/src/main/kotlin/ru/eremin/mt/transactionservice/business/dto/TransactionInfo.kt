package ru.eremin.mt.transactionservice.business.dto

import java.math.BigDecimal
import java.util.*
import ru.eremin.mt.common.model.domain.TransactionType

data class TransactionInfo(
    val amount: BigDecimal,
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val fromAccount: UUID,
    val toAccount: UUID,
    val transactionType: TransactionType
)