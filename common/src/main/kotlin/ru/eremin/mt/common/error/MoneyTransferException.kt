package ru.eremin.mt.common.error

class MoneyTransferException(
    val code: String,
    override val message: String,
    val displayMessage: String
) : RuntimeException(message) {
    constructor(error: Error) : this(
        error.code,
        error.message,
        error.displayMessage
    )
}