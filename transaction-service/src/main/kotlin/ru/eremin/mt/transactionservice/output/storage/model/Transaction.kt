package ru.eremin.mt.transactionservice.output.storage.model

import java.math.BigDecimal
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Transaction(
    @Id
    val id: String,
    val from: SideInfo?,
    val to: SideInfo?,
    val commission: BigDecimal,
    val conversationRate: BigDecimal?,
    val status: TransactionStatus
)

class SideInfo(
    val currency: Currency,
    val account: String,
    val amount: BigDecimal
)