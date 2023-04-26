package ru.eremin.mt.transactionservice.business.service

import java.math.BigDecimal
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.domain.TransactionType

interface CommissionService {

    fun getCommission(type: TransactionType): Mono<BigDecimal>
}