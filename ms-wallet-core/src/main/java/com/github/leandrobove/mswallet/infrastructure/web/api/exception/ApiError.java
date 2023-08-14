package com.github.leandrobove.mswallet.infrastructure.web.api.exception;

import java.time.OffsetDateTime;

public record ApiError(Integer status, String message, OffsetDateTime timestamp) {
}
