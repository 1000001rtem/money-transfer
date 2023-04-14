package ru.eremin.mt.accountservice.business.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import org.springframework.hateoas.RepresentationModel

class AccountDto(
    val id: UUID,
    val currency: Currency,
    val balance: BigDecimal,
    val ownerId: UUID,
    val createdAt: LocalDateTime,
    var updateAt: LocalDateTime
) : RepresentationModel<AccountDto>()