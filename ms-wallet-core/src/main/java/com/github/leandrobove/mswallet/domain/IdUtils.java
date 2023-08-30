package com.github.leandrobove.mswallet.domain;

import java.util.UUID;

public abstract class IdUtils implements DomainObject {

    private IdUtils() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }
}
