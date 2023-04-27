package ru.eremin.mt.transactionservice.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IdGenerator {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddAAA")
        private val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toList()
        const val prefix = "TRA"

        fun generateId(): String {
            val builder = StringBuilder()
            val time = LocalDateTime.now().format(formatter)
            builder.append(prefix, time)
            for (i in 0..5) {
                builder.append(chars.random())
            }
            return builder.toString()
        }
    }
}

