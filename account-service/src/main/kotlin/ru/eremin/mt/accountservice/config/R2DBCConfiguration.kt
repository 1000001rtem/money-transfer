package ru.eremin.mt.accountservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import ru.eremin.mt.accountservice.config.converter.CurrencyToStringConverter
import ru.eremin.mt.accountservice.config.converter.StringToCurrencyConverter

@Configuration
class R2DBCConfiguration {

    @Bean
    fun r2dbcCustomConverters(): R2dbcCustomConversions = R2dbcCustomConversions.of(
        PostgresDialect.INSTANCE,
        listOf(CurrencyToStringConverter(), StringToCurrencyConverter())
    )
}