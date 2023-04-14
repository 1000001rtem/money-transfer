package ru.eremin.mt.paymentsystemadapter.output

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.eremin.mt.common.model.Transaction

class IPSProducer {
    fun sendTransfer(transaction: Transaction): Mono<Boolean> {
        return true.toMono()
    }
}