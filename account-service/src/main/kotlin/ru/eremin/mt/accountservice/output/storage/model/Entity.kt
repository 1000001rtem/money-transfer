package ru.eremin.mt.accountservice.output.storage.model

import java.time.LocalDateTime

abstract class Entity(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updateAt: LocalDateTime = LocalDateTime.now()
)
