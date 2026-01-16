package org.springdoc.webflux.ui;

import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.SpringWebProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 🛠️ A targeted replacement for {@link SwaggerWelcomeWebFlux} that builds the base URI without
 * {@code UriComponentsBuilder.fromHttpRequest}, which was removed in Spring Framework 7.
 * <p>
 * Encapsulates the workaround so this class stays compliant with the Open/Closed Principle and leaves consumers
 * trusting the {@link SwaggerWelcomeWebFlux} abstraction (Liskov).
 * <p>
 * 🛠️ Reemplazo focalizado de {@link SwaggerWelcomeWebFlux} que construye la URI base sin
 * {@code UriComponentsBuilder.fromHttpRequest}, ausente en Spring Framework 7. Mantiene la solución encapsulada
 * para seguir el Principio Open/Closed y permite que los consumidores confíen en la abstracción {@link SwaggerWelcomeWebFlux}.
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public final class PatchedSwaggerWelcomeWebFlux extends SwaggerWelcomeWebFlux {

	/**
	 * 🔧 Delegates necessary dependencies to the parent while keeping the dependency graph explicit.
	 *
	 * @param swaggerUiConfigProperties Swagger UI configuration / configuración de Swagger UI
	 * @param springDocConfigProperties SpringDoc configuration / configuración de SpringDoc
	 * @param swaggerUiConfigParameters Swagger UI parameters / parámetros del UI de Swagger
	 * @param springWebProvider         provider for Spring Web components / proveedor para componentes web de Spring
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public PatchedSwaggerWelcomeWebFlux(SwaggerUiConfigProperties swaggerUiConfigProperties,
			SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters,
			SpringWebProvider springWebProvider) {
		super(swaggerUiConfigProperties, springDocConfigProperties, swaggerUiConfigParameters, springWebProvider);
	}

	/**
	 * 🧭 Builds the Swagger config URL from the current request context so the welcome page remains reachable.
	 * <p>
	 * Construye la URL de configuración de Swagger desde el contexto actual para que la página de bienvenida siga accesible.
	 *
	 * @param request current HTTP request / la solicitud HTTP actual
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	@Override
	void buildFromCurrentContextPath(ServerHttpRequest request) {
		init();
		contextPath = request.getPath().contextPath().value();
		String requestPath = request.getPath().toString();
		String baseUri = request.getURI().toString();
		if (!"/".equals(requestPath)) {
			baseUri = baseUri.replace(requestPath, "");
		}
		buildConfigUrl(UriComponentsBuilder.fromUriString(baseUri));
	}
}
