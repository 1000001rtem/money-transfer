package ru.eremin.mt.currencyservice.output.client

import java.math.BigDecimal
import reactor.core.publisher.Mono

interface RateClient {
    fun getRate(fromCurrencyCode: String, toCurrencyCode: String): Mono<BigDecimal>
}