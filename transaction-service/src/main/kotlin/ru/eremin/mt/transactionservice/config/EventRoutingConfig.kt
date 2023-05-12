package ru.eremin.mt.transactionservice.config

import org.springframework.cloud.function.context.MessageRoutingCallback
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import ru.eremin.mt.common.constan.EVENT_TYPE_HEADER

@Component
class EventRoutingConfig(
    val serviceProperties: ServiceProperties
) {

    @Bean
    fun messageRoutingCallback(): MessageRoutingCallback = object : MessageRoutingCallback {
        override fun routingResult(message: Message<*>?): String =
            message
                ?.headers?.get(EVENT_TYPE_HEADER)
                ?.let { serviceProperties.eventBindings[it] }
                ?: DEFAULT_LISTENER
    }

    companion object {
        const val DEFAULT_LISTENER = "emptyListener"
    }
}