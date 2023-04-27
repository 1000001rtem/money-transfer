package ru.eremin.mt.common.model.response

import java.time.LocalDateTime
import ru.eremin.mt.common.model.common.Error

abstract class MtResponse(val timestamp: LocalDateTime = LocalDateTime.now())
data class MtDataResponse<T>(val data: T) : MtResponse()
data class MtErrorResponse(val error: Error) : MtResponse()