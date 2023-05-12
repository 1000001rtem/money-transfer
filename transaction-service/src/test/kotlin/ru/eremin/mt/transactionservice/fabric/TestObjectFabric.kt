package ru.eremin.mt.transactionservice.fabric

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import ru.eremin.mt.common.model.domain.AccountDto
import ru.eremin.mt.common.model.domain.SideInfo
import ru.eremin.mt.common.model.domain.TransactionStatus
import ru.eremin.mt.common.model.domain.TransactionType
import ru.eremin.mt.transactionservice.business.dto.TransactionInfo
import ru.eremin.mt.transactionservice.business.transaction.person.PersonTransactionContext
import ru.eremin.mt.transactionservice.output.storage.model.TransactionDocument
import ru.eremin.mt.transactionservice.util.IdGenerator


const val TEST_USER = "testUser"
const val USD = "USD"

fun personTransactionInfo(
    amount: BigDecimal = BigDecimal.valueOf(42),
    fromCurrency: Currency = Currency.getInstance(USD),
    toCurrency: Currency = Currency.getInstance(USD),
    fromAccount: UUID = UUID.randomUUID(),
    toAccount: UUID = UUID.randomUUID(),
    transactionType: TransactionType = TransactionType.PERSON
) = TransactionInfo(amount, fromCurrency, toCurrency, fromAccount, toAccount, transactionType)

fun personTransactionContext(
    info: TransactionInfo = personTransactionInfo(),
    userId: String = TEST_USER,
) = PersonTransactionContext(info, userId)

fun accountDto(
    id: UUID = UUID.randomUUID(),
    currency: Currency = Currency.getInstance(USD),
    balance: BigDecimal = BigDecimal.valueOf(42000),
    ownerId: String = TEST_USER,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updateAt: LocalDateTime = LocalDateTime.now()
) = AccountDto(id, currency, balance, ownerId, createdAt, updateAt)

fun sideInfo(
    currency: Currency = Currency.getInstance(USD),
    account: UUID = UUID.randomUUID(),
    amount: BigDecimal = BigDecimal.valueOf(42)
) = SideInfo(currency, account, amount)

fun fullPersonTransaction(
    id: String = IdGenerator.generateId(),
    from: SideInfo? = sideInfo(),
    to: SideInfo? = sideInfo(),
    commission: BigDecimal? = BigDecimal("1.42"),
    conversationRate: BigDecimal? = null,
    status: TransactionStatus = TransactionStatus.PREPARE,
    userId: String = TEST_USER,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    comment: String? = null
) = TransactionDocument(id, from, to, commission, conversationRate, status, userId, createdAt, updatedAt, comment)