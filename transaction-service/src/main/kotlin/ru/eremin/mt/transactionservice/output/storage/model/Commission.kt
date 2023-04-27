package ru.eremin.mt.transactionservice.output.storage.model

import java.math.BigDecimal
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.eremin.mt.common.model.domain.TransactionType

@Document
data class Commission(
    @Id
    val id: Int = 1,
    val commissions: Map<TransactionType, BigDecimal>
)
