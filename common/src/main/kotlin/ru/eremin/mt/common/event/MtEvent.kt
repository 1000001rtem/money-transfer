package ru.eremin.mt.common.event

import java.time.LocalDateTime

abstract class MtEvent {
    val createdAt = LocalDateTime.now()
}