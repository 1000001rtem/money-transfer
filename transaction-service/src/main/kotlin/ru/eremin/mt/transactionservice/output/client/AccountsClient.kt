package ru.eremin.mt.transactionservice.output.client

import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.domain.AccountDto

@Component
class AccountsClient(
    private val webClient: WebClient,
    @Value("\${service.client.account.url}")
    private val url: String
) {

    fun findById(accountId: UUID): Mono<AccountDto> =
        webClient
            .get()
            .uri {
                it
                    .path(url)
                    .queryParam("id", accountId)
                    .build()
            }
            .retrieve()
            .bodyToMono(AccountDto::class.java)
}