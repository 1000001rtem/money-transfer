package ru.eremin.mt.transactionservice.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IdGeneratorTest {
    @Test
    fun `should generate id`() {
        assertEquals(23, IdGenerator.generateId().length)
    }
}