package ru.eremin.mt.accountservice.output.storage.repository

import java.util.*
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.eremin.mt.accountservice.output.storage.model.Reservation

interface ReservationRepository : ReactiveCrudRepository<Reservation, UUID>