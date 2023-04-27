package ru.eremin.mt.transactionservice.util.error

import ru.eremin.mt.common.error.Error

enum class Errors(
    override val message: String,
    override val displayMessage: String
) : Error {
    DIFFERENT_CURRENCY_ERROR(
        message = "Currency must be the same for this type of transactions",
        displayMessage = "Currency must be the same for this type of transactions"
    ),
    NOT_IMPLEMENTED_TRANSACTION_TYPE(
        message = "This type of transactions does not support",
        displayMessage = "This type of transactions does not support"
    ),
    COMMISSION_NOT_FOUND(
        message = "Can`t find commission for type: %s",
        displayMessage = "Something went wrong"
    ),
    INSUFFICIENT_FUNDS(
        message = "Insufficient funds for transaction: %s",
        displayMessage = "Insufficient funds"
    ),
    TOTAL_AMOUNT_ERROR(
        message = "Can`t get total amount for transaction %s",
        displayMessage = "Something went wrong"
    )
    ;

    override val code: String = name
}