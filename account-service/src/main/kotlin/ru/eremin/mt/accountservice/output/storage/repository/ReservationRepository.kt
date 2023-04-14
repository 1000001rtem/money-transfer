package ru.eremin.mt.accountservice.output.storage.repository

import java.util.*
import org.springframework.data.repository.CrudRepository
import ru.eremin.mt.accountservice.output.storage.model.Reservation

interface ReservationRepository : CrudRepository<Reservation, UUID>