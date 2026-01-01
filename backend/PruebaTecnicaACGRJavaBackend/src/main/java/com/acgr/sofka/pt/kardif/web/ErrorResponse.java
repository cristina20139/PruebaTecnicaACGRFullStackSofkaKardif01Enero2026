package com.acgr.sofka.pt.kardif.web;

import java.util.Map;

public record ErrorResponse(String message, Map<String, String> errors) {
}
