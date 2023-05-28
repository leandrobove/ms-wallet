package com.github.com.leandrobove.mswallet.web.exception;

import java.time.OffsetDateTime;

public record Error(Integer status, String message, OffsetDateTime timestamp) {
}
