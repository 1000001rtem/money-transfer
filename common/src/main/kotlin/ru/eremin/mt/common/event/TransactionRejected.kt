package ru.eremin.mt.common.event

data class TransactionRejected(
    val transactionId: String,
    val comment: String?,
) : MtEvent()
