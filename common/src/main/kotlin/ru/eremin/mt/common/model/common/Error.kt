package ru.eremin.mt.common.model.common

data class Error(
    val code: String,
    val message: String,
    val displayMessage: String?
)
