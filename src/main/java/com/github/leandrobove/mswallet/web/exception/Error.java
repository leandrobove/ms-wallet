package com.github.leandrobove.mswallet.web.exception;

import java.time.OffsetDateTime;

public record Error(Integer status, String message, OffsetDateTime timestamp) {
}
