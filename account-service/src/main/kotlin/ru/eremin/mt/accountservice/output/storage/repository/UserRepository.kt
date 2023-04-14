package ru.eremin.mt.accountservice.output.storage.repository

import java.util.*
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.eremin.mt.accountservice.output.storage.model.User

interface UserRepository : ReactiveCrudRepository<User, UUID>