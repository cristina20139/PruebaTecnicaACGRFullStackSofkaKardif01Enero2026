package org.springdoc.webflux.ui;

import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.SpringWebProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * A small replacement for {@link SwaggerWelcomeWebFlux} that builds the base URI without
 * calling {@code UriComponentsBuilder.fromHttpRequest}, which is not available in
 * Spring Framework 7.
 */
public final class PatchedSwaggerWelcomeWebFlux extends SwaggerWelcomeWebFlux {

	public PatchedSwaggerWelcomeWebFlux(SwaggerUiConfigProperties swaggerUiConfigProperties,
			SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters,
			SpringWebProvider springWebProvider) {
		super(swaggerUiConfigProperties, springDocConfigProperties, swaggerUiConfigParameters, springWebProvider);
	}

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
