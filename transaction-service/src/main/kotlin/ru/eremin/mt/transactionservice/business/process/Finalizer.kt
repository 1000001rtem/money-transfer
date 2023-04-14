package ru.eremin.mt.transactionservice.business.process

import reactor.core.publisher.Mono

interface Finalizer<C : Context, R> {
    fun finalize(context: C): Mono<R>
}