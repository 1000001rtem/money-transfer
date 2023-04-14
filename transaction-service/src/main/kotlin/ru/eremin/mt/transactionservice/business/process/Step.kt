package ru.eremin.mt.transactionservice.business.process

import reactor.core.publisher.Mono

interface Step<C : Context> {

    fun isApply(context: C): Boolean

    fun apply(context: C): Mono<C>
}