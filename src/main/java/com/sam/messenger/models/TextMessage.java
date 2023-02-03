package com.sam.messenger.models;

public class TextMessage extends Message {
    private final String value;

    public TextMessage(String value, Long dialogId) {
        this.value = value;
        this.dialogId = dialogId;
    }

    public String getValue() {
        return value;
    }
}
