package ru.eremin.mt.accountservice.business.dto

import java.time.LocalDateTime

abstract class AbstractDto (
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updateAt: LocalDateTime = LocalDateTime.now()
)