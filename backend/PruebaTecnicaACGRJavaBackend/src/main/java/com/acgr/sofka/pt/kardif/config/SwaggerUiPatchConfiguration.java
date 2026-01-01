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
 * Provides a replacement {@link SwaggerWelcomeWebFlux} bean that does not rely on
 * {@code UriComponentsBuilder.fromHttpRequest}, which no longer exists in Spring Framework 7.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "springdoc.use-management-port", havingValue = "false", matchIfMissing = true)
public class SwaggerUiPatchConfiguration {

	@Bean
	public SwaggerWelcomeWebFlux swaggerWelcome(SwaggerUiConfigProperties swaggerUiConfigProperties,
			SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters,
			SpringWebProvider springWebProvider) {
		return new PatchedSwaggerWelcomeWebFlux(swaggerUiConfigProperties, springDocConfigProperties,
				swaggerUiConfigParameters, springWebProvider);
	}
}
