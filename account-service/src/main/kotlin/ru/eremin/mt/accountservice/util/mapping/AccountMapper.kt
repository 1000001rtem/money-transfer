package ru.eremin.mt.accountservice.util.mapping

import ru.eremin.mt.accountservice.output.storage.model.Account
import ru.eremin.mt.common.model.domain.AccountDto

fun Account.toDto() = AccountDto(
    id = this.id,
    currency = this.currency,
    balance = this.balance,
    ownerId = this.ownerId.toString(),
    createdAt = this.createdAt,
    updateAt = this.updatedAt
)
