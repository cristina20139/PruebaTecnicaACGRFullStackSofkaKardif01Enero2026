package com.acgr.sofka.pt.kardif.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 🧾 Represents the response returned when a transaction is created or fetched.
 * <p>
 * Keeps the DTO focused on serialization concerns, depends on {@link TransactionRecord} for data (Dependency
 * Inversion) and stays open for new fields by centralizing the mapping logic.
 * <p>
 * Representa la respuesta enviada cuando se crea o consulta una transacción; mantiene el DTO centrado en la
 * serialización, depende de {@link TransactionRecord} para los datos y permanece abierto para nuevos campos.
 *
 * @param id             transaction identifier / identificador de transacción
 * @param amount         processed amount / monto procesado
 * @param commission     applied commission / comisión aplicada
 * @param commissionRate commission rate used / tasa de comisión usada
 * @param reason         reason text / texto de razón
 * @param executedAt     execution timestamp / marca temporal de ejecución
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public record TransactionResponse(Long id,
                                  BigDecimal amount,
                                  BigDecimal commission,
                                  BigDecimal commissionRate,
                                  String reason,
                                  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                  LocalDateTime executedAt) {

    /**
     * 🔄 Maps a {@link TransactionRecord} to {@link TransactionResponse} while keeping the service logic decoupled.
     *
     * @param record         persisted record / registro persistido
     * @param commissionRate rate applied / tasa aplicada
     * @param reason         reason for commission / razón para la comisión
     * @return response ready for serialization / respuesta lista para serializar
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public static TransactionResponse from(TransactionRecord record, BigDecimal commissionRate, String reason) {
        return new TransactionResponse(
                record.getId(),
                record.getAmount(),
                record.getCommission(),
                commissionRate,
                reason,
                record.getExecutedAt()
        );
    }
}
