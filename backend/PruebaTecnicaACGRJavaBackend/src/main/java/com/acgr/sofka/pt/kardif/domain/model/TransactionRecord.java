package com.acgr.sofka.pt.kardif.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 🧾 Represents the transaction row persisted in the database while keeping responsibility strictly to the
 * persistence contract (Single Responsibility Principle).
 * <p>
 * Exposes only getters/setters while downstream services depend on this abstraction (Dependency Inversion) and
 * remains closed for modification by avoiding embedded business logic, so new fields can be added without changing
 * existing consumers (Open/Closed).
 * <p>
 * 🧾 Representa la fila de transacción persistida en la base de datos, manteniendo la responsabilidad
 * estrictamente sobre el contrato de persistencia (Principio de Responsabilidad Única).
 * <p>
 * Expone solo getters/setters mientras los servicios posteriores dependen de esta abstracción (Inversión de
 * Dependencias) y permanece cerrado a modificaciones para evitar lógica de negocio incrustada, por lo que nuevos
 * campos pueden añadirse sin cambiar los consumidores existentes (Open/Closed).
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@Table("transactions")
public class TransactionRecord {

    @Id
    private Long id;

    @Column("amount")
    private BigDecimal amount;

    @Column("commission")
    private BigDecimal commission;

    @Column("executed_at")
    private LocalDateTime executedAt;

    /**
     * 🔐 Default constructor required by the R2DBC mapper so the framework can hydrate the entity without
     * injecting any behavior, keeping the constructor lightweight per Single Responsibility.
     * <p>
     * 🔐 Constructor predeterminado que necesita el mapeador R2DBC para hidratar la entidad sin inyectar
     * comportamiento, manteniendo el constructor liviano según la Responsabilidad Única.
     *
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public TransactionRecord() {
        // Required for R2DBC mapping.
    }

    /**
     * 📦 Convenience constructor that allows creating immutable-leaning records while still staying open to
     * future fields; callers depend on this to build fixtures without touching the persistence framework.
     * <p>
     * 📦 Constructor de conveniencia que permite crear registros orientados a inmutabilidad sin dejar de estar
     * abierto a campos futuros; quienes lo invocan dependen de él para construir escenarios sin manipular el
     * framework de persistencia.
     *
     * @param id          database identifier (nullable until persisted)
     * @param amount      transaction amount
     * @param commission  computed commission for the transaction
     * @param executedAt  timestamp when the transaction executed
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public TransactionRecord(Long id, BigDecimal amount, BigDecimal commission, LocalDateTime executedAt) {
        this.id = id;
        this.amount = amount;
        this.commission = commission;
        this.executedAt = executedAt;
    }

    /**
     * 📌 Getter for the persistence identifier; remains simple to honor Single Responsibility and never mutates state.
     * <p>
     * 📌 Getter del identificador persistido; mantiene la sencillez para respetar la Responsabilidad Única y no
     * muta el estado.
     *
     * @return the persisted id value
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public Long getId() {
        return id;
    }

    /**
     * 🧭 Getter for the transaction amount so callers program against read-only accessors (Liskov Principle).
     * <p>
     * 🧭 Getter del monto de la transacción para que los llamadores trabajen con accesores de solo lectura
     * (Principio de Liskov).
     *
     * @return the stored amount
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 💰 Getter for the commission; simple accessor keeps this class open for extension without changing signature.
     * <p>
     * 💰 Getter de la comisión; un accesor sencillo mantiene esta clase abierta a extensiones sin alterar la firma.
     *
     * @return stored commission
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * ⏱️ Getter for the execution timestamp; no logic beyond data retrieval respects Single Responsibility.
     * <p>
     * ⏱️ Getter de la marca temporal de ejecución; sin lógica adicional para respetar la Responsabilidad Única.
     *
     * @return timestamp when the transaction completed
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    /**
     * ✍️ Setter for the id; kept basic so it can be used by storage providers without introducing side effects.
     * <p>
     * ✍️ Setter del identificador; lo mantiene básico para que los proveedores de almacenamiento lo usen sin generar
     * efectos secundarios.
     *
     * @param id database identifier
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 📐 Setter for the amount acting only on internal state so other layers remain decoupled (Dependency Inversion).
     * <p>
     * 📐 Setter del monto que actúa solo sobre el estado interno para mantener desacopladas las demás capas
     * (Inversión de Dependencias).
     *
     * @param amount transaction amount
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 🧩 Setter for commission that stays isolated (Single Responsibility) and leaves rules/validation elsewhere.
     * <p>
     * 🧩 Setter de la comisión que permanece aislado (Responsabilidad Única) y deja reglas/validaciones en otro lugar.
     *
     * @param commission computed commission
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * 🕰️ Setter for the execution timestamp, simple to allow the persistence layer to hydrate the entity.
     * <p>
     * 🕰️ Setter de la marca temporal de ejecución, sencillo para que la capa de persistencia hidrate la entidad.
     *
     * @param executedAt execution time
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
