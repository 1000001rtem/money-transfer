package ru.eremin.mt.common.event

data class TransactionSucceed(
    val transactionId: String
) : MtEvent()
