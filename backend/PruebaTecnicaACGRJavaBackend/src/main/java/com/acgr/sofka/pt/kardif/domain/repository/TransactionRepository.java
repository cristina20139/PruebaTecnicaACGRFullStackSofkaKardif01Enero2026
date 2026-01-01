package com.acgr.sofka.pt.kardif.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.acgr.sofka.pt.kardif.domain.model.TransactionRecord;

/**
 * 🧱 Repository boundary for {@link TransactionRecord} that keeps CRUD operations centralized and depends on the
 * {@link ReactiveCrudRepository} abstraction so downstream services stay decoupled (Dependency Inversion Principle).
 * <p>
 * Staying an interface keeps it open for additional query methods without modifying existing clients (Open/Closed),
 * and inheriting the reactive CRUD contract honors Liskov by not breaking expected repository behavior.
 * <p>
 * 🧱 Frontera del repositorio para {@link TransactionRecord} que mantiene las operaciones CRUD centralizadas y
 * depende de la abstracción {@link ReactiveCrudRepository} para que los servicios posteriores queden desacoplados
 * (Principio de Inversión de Dependencias).
 * <p>
 * Mantenerlo como interfaz permite agregar métodos de consulta sin modificar clientes existentes (Open/Closed) y
 * heredar el contrato reactivo CRUD respeta Liskov al no romper el comportamiento esperado.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public interface TransactionRepository extends ReactiveCrudRepository<TransactionRecord, Long> {
}
