package ru.eremin.mt.transactionservice.business.transaction.person.steps

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.eremin.mt.common.model.domain.AccountDto
import ru.eremin.mt.common.model.domain.SideInfo
import ru.eremin.mt.transactionservice.business.process.Step
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.output.client.AccountsClient
import ru.eremin.mt.transactionservice.util.error.Errors.DIFFERENT_CURRENCY_ERROR
import ru.eremin.mt.transactionservice.util.error.Errors.INSUFFICIENT_FUNDS

@Component
class EnrichPersonTransaction(
    private val accountsClient: AccountsClient,
) : Step<PersonTransactionContext> {
    override fun isApply(context: PersonTransactionContext): Boolean = true

    override fun apply(context: PersonTransactionContext): Mono<PersonTransactionContext> =
        accountsClient.findById(context.transactionInfo.fromAccount)
            .doOnNext { checkAccount(it, context) }
            .map {
                context.copy(
                    transaction = context.transaction.copy(
                        from = SideInfo(
                            account = it.id,
                            amount = context.transactionInfo.amount,
                            currency = it.currency
                        ),
                        to = SideInfo(
                            account = context.transactionInfo.toAccount,
                            amount = context.transactionInfo.amount,
                            currency = context.transactionInfo.toCurrency
                        )
                    )
                )
            }

    private fun checkAccount(account: AccountDto, context: PersonTransactionContext) {
        if (account.currency != context.transactionInfo.fromCurrency) throw DIFFERENT_CURRENCY_ERROR.asException()
        if (account.balance < context.transactionInfo.amount) {
            throw INSUFFICIENT_FUNDS
                .formatMessage(context.getTransactionId())
                .asException()
        }
    }
}