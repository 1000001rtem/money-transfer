package ru.eremin.mt.accountservice.util.mapping

import ru.eremin.mt.accountservice.business.dto.AccountDto
import ru.eremin.mt.accountservice.output.storage.model.Account

fun Account.toDto() = AccountDto(
    id = this.id,
    currency = this.currency,
    balance = this.balance,
    ownerId = this.owner.id,
    createdAt = this.createdAt,
    updateAt = this.updateAt
)
