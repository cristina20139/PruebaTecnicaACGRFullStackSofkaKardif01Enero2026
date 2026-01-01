package com.acgr.sofka.pt.kardif.config;

import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.SpringWebProvider;
import org.springdoc.webflux.ui.PatchedSwaggerWelcomeWebFlux;
import org.springdoc.webflux.ui.SwaggerWelcomeWebFlux;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 🛠️ Provides a replacement {@link SwaggerWelcomeWebFlux} bean that avoids the removed
 * {@code UriComponentsBuilder.fromHttpRequest} in Spring Framework 7 while keeping consumers decoupled.
 * <p>
 * Encapsulates the workaround to honor Single Responsibility, allows clients to extend the welcome page (Open/Closed),
 * and exposes the same abstraction for downstream consumers (Liskov).
 * <p>
 * Proporciona el bean de bienvenida de Swagger sin depender del método eliminado por Spring Framework 7 y mantiene
 * a los consumidores desacoplados. Encapsula la solución para respetar el Principio de Responsabilidad Única,
 * permite extensiones abiertas y sigue exponiendo la misma abstracción para garantizar Liskov.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "springdoc.use-management-port", havingValue = "false", matchIfMissing = true)
public class SwaggerUiPatchConfiguration {

	@Bean
	/**
	 * 🔌 Constructs the patched welcome bean so callers can program against {@link SwaggerWelcomeWebFlux} while
	 * benefiting from future improvements, keeping Dependency Inversion intact.
	 * <p>
	 * Localiza el cableado para respetar Responsabilidad Única y permanece abierto para nuevas extensiones sin afectar
	 * a otros consumidores (Open/Closed y Liskov).
	 *
	 * @param swaggerUiConfigProperties Swagger UI configuration / configuración de Swagger UI
	 * @param springDocConfigProperties SpringDoc configuration / configuración de SpringDoc
	 * @param swaggerUiConfigParameters Swagger UI parameters / parámetros del UI de Swagger
	 * @param springWebProvider provider for Spring Web components / proveedor para componentes web de Spring
	 * @return the patched welcome bean / el bean de bienvenida parcheado
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public SwaggerWelcomeWebFlux swaggerWelcome(SwaggerUiConfigProperties swaggerUiConfigProperties,
			SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters,
			SpringWebProvider springWebProvider) {
		return new PatchedSwaggerWelcomeWebFlux(swaggerUiConfigProperties, springDocConfigProperties,
				swaggerUiConfigParameters, springWebProvider);
	}
}
