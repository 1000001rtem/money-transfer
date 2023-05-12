package ru.eremin.mt.transactionservice.output.messaging

import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import ru.eremin.mt.common.constan.EVENT_TYPE_HEADER
import ru.eremin.mt.common.event.MtEvent

@Component
class EventSender(
    private val stream: StreamBridge
) {
    fun send(event: MtEvent) {
        stream.send(
            MT_EVENTS_DESTINATION,
            MessageBuilder
                .withPayload(event)
                .setHeader(EVENT_TYPE_HEADER, event.javaClass.simpleName)
                .build()
        )
    }

    companion object {
        const val MT_EVENTS_DESTINATION = "money-transfer-events-out"
    }
}