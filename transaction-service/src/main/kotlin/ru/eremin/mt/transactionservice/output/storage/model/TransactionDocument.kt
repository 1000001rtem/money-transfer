package ru.eremin.mt.transactionservice.output.storage.model

import java.math.BigDecimal
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.eremin.mt.common.model.domain.SideInfo
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.util.IdGenerator

@Document
data class TransactionDocument(
    @Id
    val id: String = IdGenerator.generateId(),
    val from: SideInfo? = null,
    val to: SideInfo? = null,
    val commission: BigDecimal? = null,
    val conversationRate: BigDecimal? = null,
    val status: TransactionStatus,
    val userId: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    val comment: String? = null
){
    fun totalAmount() = from?.amount?.plus(from.amount.multiply(commission))
}