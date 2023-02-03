package com.sam.messenger.events;

import com.sam.messenger.models.Message;

public class ReceivedMessageEvent {
    private final Message message;

    private ReceivedMessageEvent(Message message) {
        this.message = message;
    }

    public static ReceivedMessageEvent create(Message message) {
        return new ReceivedMessageEvent(message);
    }

    public Message getMessage() {
        return message;
    }
}
