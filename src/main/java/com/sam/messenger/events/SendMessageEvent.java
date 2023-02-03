package com.sam.messenger.events;

import com.sam.messenger.models.Message;

public class SendMessageEvent {
    private final Message message;

    private SendMessageEvent(Message message) {
        this.message = message;
    }

    public static SendMessageEvent create(Message message) {
        return new SendMessageEvent(message);
    }

    public Message getMessage() {
        return message;
    }
}
