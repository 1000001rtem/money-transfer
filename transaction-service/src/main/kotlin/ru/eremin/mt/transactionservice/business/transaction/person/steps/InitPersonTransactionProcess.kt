package ru.eremin.mt.transactionservice.business.transaction.person.steps

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.process.Step
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.output.storage.repository.TransactionRepository
import ru.eremin.mt.transactionservice.util.error.Errors.DIFFERENT_CURRENCY_ERROR

@Component
class InitPersonTransactionProcess(
    private val transactionRepository: TransactionRepository
) : Step<PersonTransactionContext> {
    override fun isApply(context: PersonTransactionContext): Boolean = true

    override fun apply(context: PersonTransactionContext): Mono<PersonTransactionContext> {
        return checkCurrencies(context.transactionInfo)
            .then(Mono.defer {
                transactionRepository.save(context.transaction)
                    .map { context.copy(transaction = it) }
                    .doOnNext { log.info("Create transaction with id: ${it.getTransactionId()}") }
            })
            .publishOn(Schedulers.boundedElastic())
    }

    private fun checkCurrencies(transactionInfo: TransactionInfo): Mono<Void> {
        val fromCurrency = transactionInfo.fromCurrency
        val toCurrency = transactionInfo.toCurrency
        if (fromCurrency != toCurrency) return Mono.error(DIFFERENT_CURRENCY_ERROR.asException())
        return Mono.empty()
    }

    companion object {
        val log = LoggerFactory.getLogger(this::class.java)
    }
}