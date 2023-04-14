package ru.eremin.mt.currencyservice.input

import java.math.BigDecimal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.eremin.mt.currencyservice.business.service.RateService

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyController(
    private val rateService: RateService
) {

    @GetMapping
    fun getRate(
        @RequestParam("fromCurrency") fromCurrencyCode: String,
        @RequestParam("toCurrency") toCurrencyCode: String
    ): Mono<BigDecimal> = rateService.getRate(fromCurrencyCode, toCurrencyCode)
}