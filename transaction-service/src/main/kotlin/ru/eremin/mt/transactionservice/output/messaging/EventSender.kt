package ru.eremin.mt.transactionservice.output.messaging

import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.eremin.mt.common.event.MtEvent

@Component
class EventSender(
    private val stream: StreamBridge
) {
    fun send(event: MtEvent) {
        stream.send(
            "",
            MessageBuilder.withPayload(event)
        )
    }
}