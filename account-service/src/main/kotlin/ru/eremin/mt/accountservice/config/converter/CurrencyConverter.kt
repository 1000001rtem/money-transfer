package ru.eremin.mt.accountservice.config.converter

import java.util.*
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class CurrencyToStringConverter : Converter<Currency, String> {
    override fun convert(source: Currency): String? {
        return source.currencyCode
    }
}

@ReadingConverter
class StringToCurrencyConverter : Converter<String, Currency> {
    override fun convert(source: String): Currency? {
        return Currency.getInstance(source)
    }
}