package com.acgr.sofka.pt.kardif.web;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

/**
 * 🧯 Centralizes web error responses so controllers remain focused on happy paths.
 * <p>
 * Adheres to Single Responsibility by keeping exception handling in one place, remains open by mapping new handlers
 * without touching controllers, and depends on {@link ErrorResponse} abstractions (Dependency Inversion).
 * <p>
 * Centraliza las respuestas de error web para que los controladores se concentren en los estados exitosos. Mantiene
 * la responsabilidad única, permite expandir manejadores sin tocar los controladores y depende de {@link ErrorResponse}.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@RestControllerAdvice
public class GlobalErrorHandler {

    /**
     * 🧾 Processes validation errors so API consumers receive structured feedback.
     * <p>
     * Maneja errores de validación para que los consumidores reciban retroalimentación estructurada.
     *
     * @param ex validation exception / excepción de validación
     * @return error response / respuesta de error
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleValidationErrors(WebExchangeBindException ex) {
        Map<String, String> violations = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (first, second) -> second));
        return Mono.just(new ErrorResponse("Solicitud invalida", violations));
    }

    /**
     * 🔍 Catches unexpected errors so the API consistently returns a safe payload.
     * <p>
     * Captura errores inesperados para que la API devuelva siempre un payload seguro.
     *
     * @param ex unexpected exception / excepción inesperada
     * @return error response / respuesta de error
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleUnexpectedError(Exception ex) {
        return Mono.just(new ErrorResponse("Se presento un error interno", Map.of("error", ex.getMessage())));
    }
}
