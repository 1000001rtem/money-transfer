package ru.eremin.mt.accountservice.output.storage.model

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable

abstract class Entity<ID>(
    @Id
    private var id: ID,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    @Transient
    var isNew: Boolean? = null
) : Persistable<ID> {

    override fun getId() = id

    override fun isNew() = isNew ?: false
}
