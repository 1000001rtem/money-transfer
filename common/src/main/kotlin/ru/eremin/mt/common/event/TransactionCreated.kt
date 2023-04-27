package ru.eremin.mt.common.event

import ru.eremin.mt.common.model.domain.Transaction

data class TransactionCreated(
    val transaction: Transaction
) : MtEvent()