package ru.eremin.mt.transactionservice.business.transaction.person

import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.process.Context
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument

data class PersonTransactionContext(
    val transactionInfo: TransactionInfo,
    val userId: String,
    val transaction: TransactionDocument = TransactionDocument(
        status = TransactionStatus.PREPARE,
        userId = userId
    ),
) : Context {

    override fun getTransactionId() = transaction.id
}