package ru.eremin.mt.transactionservice.business.process

interface Context {
    fun getTransactionId(): String
}