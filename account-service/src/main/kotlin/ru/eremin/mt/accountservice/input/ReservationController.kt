package ru.eremin.mt.accountservice.input

import java.util.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.eremin.mt.accountservice.business.service.ReservationService
import ru.eremin.mt.accountservice.input.dto.OperationRequest

@RestController
@RequestMapping("api/v1/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {

    @GetMapping
    fun findByAccountId(@RequestParam("accountId") accountId: UUID) =
        reservationService.findReservationsByAccount(accountId)

    @PostMapping("/reserve")
    fun reserve(@RequestBody request: OperationRequest) =
        reservationService.reserveFunds(
            accountId = request.accountId,
            amount = request.amount,
            currency = Currency.getInstance(request.currencyCode)
        )

    @PostMapping("/release")
    fun release(@RequestBody request: OperationRequest) =
        reservationService.releaseFunds(
            accountId = request.accountId,
            amount = request.amount,
            currency = Currency.getInstance(request.currencyCode)
        )
}