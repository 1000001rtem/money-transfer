package ru.eremin.mt.accountservice.output.storage.model

import java.util.*
import org.springframework.data.annotation.Id

class User(
    @Id
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val email: String
) : Entity()