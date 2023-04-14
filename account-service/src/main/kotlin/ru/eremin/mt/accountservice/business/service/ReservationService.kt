package ru.eremin.mt.accountservice.business.service

import java.math.BigDecimal
import java.util.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.eremin.mt.accountservice.business.dto.ReservationDto

interface ReservationService {

    fun findReservationsByAccount(accountId: UUID): Flux<ReservationDto>

    fun reserveFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean>

    fun releaseFunds(accountId: UUID, amount: BigDecimal, currency: Currency): Mono<Boolean>

}