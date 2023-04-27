package ru.eremin.mt.transactionservice.util.mapper

import ru.eremin.mt.common.model.domain.Transaction
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument

fun TransactionDocument.toDto(): Transaction = Transaction(
    id = this.id,
    from = this.from,
    to = this.to,
    commission = this.commission,
    conversationRate = this.conversationRate,
    status = this.status,
    userId = this.userId,
    createAt = this.createdAt,
    updatedAt = this.updatedAt,
    comment = this.comment,
)
