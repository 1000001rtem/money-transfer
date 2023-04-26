package ru.eremin.mt.common.error

interface Error {
    val code: String
    val message: String
    val displayMessage: String


    fun asException(): MoneyTransferException = MoneyTransferException(this)

    fun formatMessage(vararg values: String): Error = FormattedError(
        this.code,
        this.message.format(*values),
        this.displayMessage
    )

    fun formatDisplayMessage(vararg values: String): Error = FormattedError(
        this.code,
        this.message,
        this.displayMessage.format(*values)
    )

    fun formatAll(vararg values: String): Error = FormattedError(
        this.code,
        this.message.format(*values),
        this.displayMessage.format(*values)
    )

    fun replaceMessages(message: String? = null, displayMessage: String? = null) = FormattedError(
        this.code,
        message ?: this.message,
        displayMessage ?: this.displayMessage
    )
}

data class FormattedError(
    override val code: String,
    override val message: String,
    override val displayMessage: String
) : Error


