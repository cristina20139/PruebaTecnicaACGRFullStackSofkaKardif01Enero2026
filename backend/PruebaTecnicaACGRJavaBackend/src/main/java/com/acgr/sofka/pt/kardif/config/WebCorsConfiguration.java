package com.acgr.sofka.pt.kardif.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * 🌐 Configures CORS escape hatches so the Angular UI can safely communicate with backend APIs.
 * <p>
 * Keeps this configuration separate (Single Responsibility), exposes abstractions for CORS policies
 * (Dependency Inversion), and stays open to additional mappings by centralizing the wiring (Open/Closed).
 * <p>
 * Configura los escapes CORS para que la UI de Angular pueda comunicar con seguridad, manteniendo la configuración
 * aislada, exponiendo abstracciones y quedando abierta a nuevas rutas al tener toda la lógica en un solo lugar.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@Configuration(proxyBeanMethods = false)
public class WebCorsConfiguration implements WebFluxConfigurer {

    @Override
    /**
     * 🧭 Registers CORS mappings so callers adhere to the documented origins, methods, and headers.
     * <p>
     * Keeps the registry wiring isolated (Single Responsibility) and leaves room to extend or restrict
     * endpoints without touching other web config pieces (Open/Closed).
     * <p>
     * Registra los mapeos CORS para que los clientes sigan los orígenes, métodos y encabezados documentados,
     * manteniendo el cableado aislado y abierto a nuevas rutas sin modificar otras partes.
     *
     * @param registry the registry used to define CORS rules / el registro usado para definir reglas CORS
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:60204")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    /**
     * 🧱 Builds a {@link CorsWebFilter} so the filter chain relies on a reusable configuration instead of
     * duplicating CORS details elsewhere.
     * <p>
     * Returning the {@link CorsWebFilter} abstraction keeps other layers decoupled and avoids sprinkling policies
     * across the codebase (Dependency Inversion and Single Responsibility).
     * <p>
     * Construye un {@link CorsWebFilter} reutilizable en lugar de repetir detalles de CORS y mantiene las demás
     * capas desacopladas devolviendo la abstracción correspondiente.
     *
     * @return the configured filter bean protecting cross-origin calls / el bean configurado que protege llamadas entre orígenes
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:60204"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
