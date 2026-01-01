package com.acgr.sofka.pt.kardif.web;

import java.util.Map;

/**
 * ⚠️ Captures API error details so handlers can expose consistent payloads.
 * <p>
 * Keeps the record lightweight (Single Responsibility) and lets controllers or advice layers depend on a simple
 * abstraction rather than rebuilding responses (Dependency Inversion).
 * <p>
 * Captura los detalles de errores de la API para que los manejadores entreguen payloads consistentes.
 * Mantiene el record ligero y permite que controladores o consejos dependan de una abstracción sencilla.
 *
 * @param message user-friendly message / mensaje amigable para el usuario
 * @param errors  field-specific errors / errores por campo
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public record ErrorResponse(String message, Map<String, String> errors) {
}
