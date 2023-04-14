package ru.eremin.mt.paymentsystemadapter.business.service

import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.Transaction

interface TransferService {
    fun sendTransfer(transaction: Transaction): Mono<Boolean>
}