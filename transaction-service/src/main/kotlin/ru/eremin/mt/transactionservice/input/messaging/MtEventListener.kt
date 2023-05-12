package ru.eremin.mt.transactionservice.input.messaging

import java.util.function.Function
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import ru.eremin.mt.common.event.MtEvent
import ru.eremin.mt.common.event.TransactionError
import ru.eremin.mt.common.event.TransactionRejected
import ru.eremin.mt.common.event.TransactionSent
import ru.eremin.mt.common.event.TransactionSucceed
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.transactionservice.business.service.TransactionService

@Component
class MtEventsListener(
    private val transactionService: TransactionService
) {

    @Bean
    fun transactionSentListener(): Function<Flux<TransactionSent>, Unit> {
        return Function {
            it
                .flatMap { event ->
                    transactionService.updateTransactionStatus(
                        transactionId = event.transactionId,
                        status = TransactionStatus.SENT
                    )
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()
        }
    }

    @Bean
    fun transactionSucceedListener(): Function<Flux<TransactionSucceed>, Unit> {
        return Function {
            it
                .flatMap { event ->
                    transactionService.updateTransactionStatus(
                        transactionId = event.transactionId,
                        status = TransactionStatus.DONE
                    )
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()
        }
    }

    @Bean
    fun transactionRejectListener(): Function<Flux<TransactionRejected>, Unit> {
        return Function {
            it
                .flatMap { event ->
                    transactionService.updateTransactionStatus(
                        transactionId = event.transactionId,
                        status = TransactionStatus.DECLINE,
                        comment = event.comment
                    )
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()
        }
    }

    @Bean
    fun transactionErrorListener(): Function<Flux<TransactionError>, Unit> {
        return Function {
            it
                .flatMap { event ->
                    transactionService.updateTransactionStatus(
                        transactionId = event.transactionId,
                        status = TransactionStatus.ERROR,
                        comment = event.comment
                    )
                }
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe()
        }
    }

    @Bean
    fun emptyListener(): Function<Message<MtEvent>, Unit> {
        return Function { }
    }
}
