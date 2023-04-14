package ru.eremin.mt.transactionservice.business.dto

import java.math.BigDecimal
import java.util.*

data class TransactionInfo(
    val amount: BigDecimal,
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val fromAccount: String,
    val toAccount: String
)