package ru.eremin.mt.transactionservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import ru.eremin.mt.transactionservice.output.storage.model.Commission
import ru.eremin.mt.transactionservice.output.storage.repository.CommissionRepository

@Component
class Bootstrap(
    private val commissionRepository: CommissionRepository,
    private val objectMapper: ObjectMapper
) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadCommissions() {
        this.javaClass.classLoader.getResourceAsStream("commission/commission.json").use {
            objectMapper.readValue(it, Commission::class.java)
                .let { commission ->
                    commissionRepository.save(commission)
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe()
                }
        }
    }
}