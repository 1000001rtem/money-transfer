package ru.eremin.mt.common.event

data class TransactionError(
    val transactionId: String,
    val comment: String?,
) : MtEvent()
