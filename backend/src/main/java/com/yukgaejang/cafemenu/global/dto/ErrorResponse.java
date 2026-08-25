package com.yukgaejang.cafemenu.global.dto;

import java.time.Instant;

public record ErrorResponse(String code, String message, Instant timestamp) {
}

