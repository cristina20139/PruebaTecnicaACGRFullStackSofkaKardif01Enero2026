package com.acgr.sofka.pt.kardif.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SwaggerUrlLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(SwaggerUrlLogger.class);
    private final Environment environment;

    public SwaggerUrlLogger(Environment environment) {
        this.environment = environment;
    }

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
