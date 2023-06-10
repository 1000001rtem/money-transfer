package ru.eremin.mt.accountservice.business.service.impl

import java.math.BigDecimal
import java.util.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.accountservice.business.service.ReservationService
import ru.eremin.mt.common.model.domain.ReservationDto

@Service
class ReservationServiceImpl : ReservationService {
    override fun findReservationsByAccount(accountId: UUID): Flux<ReservationDto> {
        TODO("Not yet implemented")
    }

    override fun reserveFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun releaseFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}