package ru.eremin.mt.transactionservice.business.service.impl

import java.math.BigDecimal
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.eremin.mt.common.model.domain.TransactionType
import ru.eremin.mt.transactionservice.business.service.CommissionService
import ru.eremin.mt.transactionservice.output.storage.model.Commission
import ru.eremin.mt.transactionservice.output.storage.repository.CommissionRepository
import ru.eremin.mt.transactionservice.util.error.Errors.COMMISSION_NOT_FOUND

@Service
class CommissionServiceImpl(
    private val commissionRepository: CommissionRepository
) : CommissionService {
    override fun getCommission(type: TransactionType): Mono<BigDecimal> =
        commissionRepository.findOne(commissionExample)
            .flatMap {
                it.commissions[type]?.toMono() ?: Mono.error(
                    COMMISSION_NOT_FOUND.formatMessage(type.toString()).asException()
                )
            }

    companion object {
        private val commissionExample = Example.of(
            Commission(commissions = emptyMap()),
            ExampleMatcher.matching().withMatcher("id") { it.exact() }
        )
    }
}