package ru.eremin.mt.transactionservice.output.storage.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import ru.eremin.mt.transactionservice.output.storage.model.Commission

interface CommissionRepository : ReactiveMongoRepository<Commission, Int>
