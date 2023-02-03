package com.sam.messenger.models;

import java.io.Serializable;

public class Message implements Serializable {
    protected Long dialogId;

    public Long getDialogId() {
        return dialogId;
    }
}
