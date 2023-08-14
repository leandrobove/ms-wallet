package com.github.leandrobove.mswallet.infrastructure.web.exception;

import java.time.OffsetDateTime;

public record Error(Integer status, String message, OffsetDateTime timestamp) {
}
