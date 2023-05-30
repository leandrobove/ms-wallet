package com.github.leandrobove.mswallet.event;

import lombok.Getter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
public abstract class BaseEvent implements Serializable {

    protected String name;
    protected OffsetDateTime timestamp;

    public BaseEvent(String name) {
        this.name = name;
        this.timestamp = OffsetDateTime.now();
    }
}
