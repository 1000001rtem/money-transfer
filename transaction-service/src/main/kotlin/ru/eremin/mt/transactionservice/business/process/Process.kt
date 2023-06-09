package ru.eremin.mt.transactionservice.business.process

import java.util.concurrent.atomic.AtomicReference
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono

class Process<C : Context, R>(
    val processName: String,
    private val steps: List<Step<C>>,
    private val finalizer: Finalizer<C, R>
) {

    fun process(context: C): Mono<R> {
        val contextReference = AtomicReference(context)
        return Mono.fromCallable {
            log.info("Start process $processName for transaction ${context.getTransactionId()}")
            contextReference
        }
            .transform { applySteps(it) }
            .flatMap { finalize(it) }
            .doOnError { finalizeError(it, context.getTransactionId()) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    private fun applySteps(contextRef: Mono<AtomicReference<C>>): Mono<AtomicReference<C>> {
        var res: Mono<AtomicReference<C>> = contextRef
        for (step in steps) {
            res = res.flatMap { applyStep(step, it) }
        }
        return res
    }

    private fun applyStep(step: Step<C>, contextRef: AtomicReference<C>): Mono<AtomicReference<C>> {
        val context = contextRef.get()
        if (!step.isApply(context)) {
            return contextRef.toMono()
        }
        log.debug("Process $processName. Apply step $step to transaction ${context.getTransactionId()}")
        return step.apply(context)
            .map {
                log.debug("Process $processName. Finish step $step for transaction ${context.getTransactionId()}")
                AtomicReference(it)
            }
    }

    private fun finalize(contextRef: AtomicReference<C>): Mono<R> {
        val context = contextRef.get()
        log.debug("Start Finalize process $processName for transaction ${context.getTransactionId()}")
        return finalizer.finalize(context)
            .doOnNext {
                log.info("Finish Process $processName for transaction ${context.getTransactionId()}")
            }
    }

    private fun finalizeError(error: Throwable, id: String) {
        log.error("Process $processName for transaction $id finished with error", error)
        finalizer.finalizeError(error, id)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(Process::class.java)
    }

}