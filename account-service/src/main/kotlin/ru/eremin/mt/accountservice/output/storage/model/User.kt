package ru.eremin.mt.accountservice.output.storage.model

import java.util.*
import org.springframework.data.relational.core.mapping.Table

@Table(name = "users")
class User(
    val firstName: String,
    val lastName: String,
    val email: String,
) : Entity<UUID>(UUID.randomUUID())