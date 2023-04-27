package ru.eremin.mt.transactionservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.eremin.mt.common.model.domain.Transaction
import ru.eremin.mt.transactionservice.business.process.Process
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionFinalizer
import ru.eremin.mt.transactionservice.business.transaction.person.steps.CheckRestrictionsPersonTransaction
import ru.eremin.mt.transactionservice.business.transaction.person.steps.EnrichPersonTransaction
import ru.eremin.mt.transactionservice.business.transaction.person.steps.GetCommissionPersonTransfer
import ru.eremin.mt.transactionservice.business.transaction.person.steps.InitPersonTransactionProcess
import ru.eremin.mt.transactionservice.business.transaction.person.steps.ReservePersonTransfer

@Configuration
class TransactionProcessConfiguration {

    @Bean
    fun personTransactionProcess(
        initPersonTransactionProcess: InitPersonTransactionProcess,
        enrichPersonTransaction: EnrichPersonTransaction,
        checkRestrictionsPersonTransaction: CheckRestrictionsPersonTransaction,
        getCommissionPersonTransfer: GetCommissionPersonTransfer,
        reservePersonTransfer: ReservePersonTransfer,
        personTransactionFinalizer: PersonTransactionFinalizer,
    ): Process<PersonTransactionContext, Transaction> {
        return Process(
            processName = "transaction/person",
            steps = listOf(
                initPersonTransactionProcess,
                enrichPersonTransaction,
                checkRestrictionsPersonTransaction,
                getCommissionPersonTransfer,
                reservePersonTransfer
            ),
            finalizer = personTransactionFinalizer
        )
    }
}