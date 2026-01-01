package com.acgr.sofka.pt.kardif.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 📢 Logs the Swagger UI endpoint once the application is ready so devs can reach the documentation easily.
 * <p>
 * Keeps responsibilities bounded to logging, keeps the rest of the infrastructure ignorant of the derivation, and
 * relies on {@link Environment} to honor Dependency Inversion and Open/Closed.
 * <p>
 * Registra el endpoint de Swagger UI cuando la aplicación está lista, manteniendo el enfoque único de registro,
 * dejando al resto del sistema ajeno a cómo se construye la URL y apoyándose en {@link Environment} para seguir la
 * Inversión de Dependencias y permanecer abierto para nuevas fuentes.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@Component
public class SwaggerUrlLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(SwaggerUrlLogger.class);
    private final Environment environment;

    public SwaggerUrlLogger(Environment environment) {
        this.environment = environment;
    }

    /**
     * 🕒 Builds the Swagger UI base URL from the environment, logs it, and leaves the logic open for other
     * consumers (Liskov and Open/Closed) while depending on {@link Environment} (Dependency Inversion).
     * <p>
     * Construye la URL base de Swagger UI desde el entorno, la registra y mantiene la lógica abierta para otros
     * consumidores, apoyándose en {@link Environment} para seguir la Inversión de Dependencias.
     *
     * @param event the event that indicates the application is ready / el evento que indica que la aplicacion esta lista; the method keeps single-task focus (SSR) / el metodo mantiene un enfoque de tarea unica (SSR)
     * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
     * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = environment.getProperty("local.server.port");
        if (!StringUtils.hasText(port)) {
            port = environment.getProperty("server.port", "8080");
        }
        String contextPath = StringUtils.trimWhitespace(
                environment.getProperty("server.servlet.context-path", ""));

        if (StringUtils.hasText(contextPath) && !contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }

        String baseUrl = String.format("http://localhost:%s%s", port,
                StringUtils.hasText(contextPath) ? contextPath : "");

        log.info("✨ Swagger UI listo: {}/swagger-ui/index.html", baseUrl);
    }
}
