package com.acgr.sofka.pt.kardif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 🧱 Centralizes Jackson serialization policies so higher layers do not need to repeat basic setup.
 * <p>
 * Keeps responsibilities isolated (Single Responsibility), stays Open/Closed through the builder, and depends on
 * {@link ObjectMapper} to maintain Dependency Inversion.
 * <p>
 * Centraliza las politicas de serializacion de Jackson para que las capas superiores no repitan esta configuracion.
 * Mantiene responsabilidades aisladas, permanece abierta para extensiones futuras y depende de la abstraccion
 * {@link ObjectMapper} para respetar la Inversion de Dependencias.
 *
 * @author Aura Cristina Garzon Rodriguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogota DC Colombia
 */
@Configuration
public class JacksonConfig {

    /**
     * 🧰 Builds the primary {@link ObjectMapper} with consistent date-handling so downstream services rely on a
     * single serialization contract.
     * <p>
     * Centralizes custom features, respects the Single Responsibility Principle, and keeps the code open for future
     * extensions by delegating to {@link Jackson2ObjectMapperBuilder}.
     * <p>
     * Construye el {@link ObjectMapper} principal con fechas consistentes, mantiene el enfoque en una unica
     * responsabilidad y sigue abierto para extensiones delegando en el builder.
     *
     * @return the customized mapper / el mapeador personalizado
     * @author Aura Cristina Garzon Rodriguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogota DC Colombia
     */
    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
}
