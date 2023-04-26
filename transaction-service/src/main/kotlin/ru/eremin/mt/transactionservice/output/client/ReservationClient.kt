package ru.eremin.mt.transactionservice.output.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.request.OperationRequest
import ru.eremin.mt.common.model.response.ReservationResult

@Component
class ReservationClient(
    private val webClient: WebClient,
    @Value("\${service.client.reservation.url}")
    private val url: String
) {

    fun reserve(request: OperationRequest): Mono<ReservationResult> =
        webClient
            .post()
            .uri(url)
            .body(BodyInserters.fromProducer(request, OperationRequest::class.java))
            .retrieve()
            .bodyToMono(ReservationResult::class.java)
}