package ru.eremin.mt.currencyservice.business.service

import java.math.BigDecimal
import reactor.core.publisher.Mono

interface RateService {
    fun getRate(fromCurrencyCode: String, toCurrencyCode: String): Mono<BigDecimal>
}