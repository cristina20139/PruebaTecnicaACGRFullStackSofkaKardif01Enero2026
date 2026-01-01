/*
 *
 *  *
 *  *  *
 *  *  *  *
 *  *  *  *  * Copyright 2019-2022 the original author or authors.
 *  *  *  *  *
 *  *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  *  * You may obtain a copy of the License at
 *  *  *  *  *
 *  *  *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *  *
 *  *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  *  * See the License for the specific language governing permissions and
 *  *  *  *  * limitations under the License.
 *  *  *  *
 *  *  *
 *  *
 *
 */
package org.springdoc.core.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import io.swagger.v3.oas.models.responses.ApiResponse;

/**
 * 🛡️ Represents a controller advice instance and tracks its metadata for building generic responses.
 * <p>
 * 🛡️ Representa una instancia de controller advice y registra su metadata para construir respuestas genéricas.
 * <p>
 * Keeps the responsibility limited to metadata tracking (Single Responsibility), stays open for additional metadata
 * fields (Open/Closed), and exposes getters so higher layers stay decoupled from the advice internals
 * (Dependency Inversion).
 *
 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
 */
public class ControllerAdviceInfo {

	/**
	 * The Controller advice bean name.
	 */
	private final String beanName;

	/**
	 * The Controller advice.
	 */
	private final Object controllerAdvice;

	/**
	 * The Controller advice annotation instance.
	 */
	private final ControllerAdvice controllerAdviceAnnotation;

	/**
	 * The Api response map.
	 */
	private final Map<String, ApiResponse> apiResponseMap = new LinkedHashMap<>();

	/**
	 * 🧱 Stores the name and instance so exception handler scanning remains predictable.
	 * <p>
	 * 🧱 Guarda el nombre y la instancia para que el escaneo siga siendo predecible.
	 *
	 * @param beanName         the bean name / el nombre del bean
	 * @param controllerAdvice the controller advice / el controller advice
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public ControllerAdviceInfo(String beanName, Object controllerAdvice) {
		this.beanName = beanName;
		this.controllerAdvice = controllerAdvice;
		this.controllerAdviceAnnotation = AnnotatedElementUtils.findMergedAnnotation(controllerAdvice.getClass(), ControllerAdvice.class);
	}

	/**
	 * 🧭 Returns the controller advice instance so consumers can inspect its handlers.
	 * <p>
	 * 🧭 Devuelve la instancia de controller advice para que los consumidores puedan inspeccionar sus manejadores.
	 *
	 * @return the controller advice / el controller advice
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public Object getControllerAdvice() {
		return controllerAdvice;
	}

	/**
	 * 📝 Returns the bean name for logging and lookup resilience.
	 * <p>
	 * 📝 Devuelve el nombre del bean para que la búsqueda y los registros sigan siendo fiables.
	 *
	 * @return the bean name / el nombre del bean
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * 🧾 Returns the joined {@link ControllerAdvice} annotation so applicability checks stay centralized.
	 * <p>
	 * 🧾 Devuelve la anotación {@link ControllerAdvice} fusionada para que la verificación de aplicabilidad siga centralizada.
	 *
	 * @return the controller advice annotation / la anotación de controller advice
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public ControllerAdvice getControllerAdviceAnnotation() {
		return controllerAdviceAnnotation;
	}

	/**
	 * 🧰 Exposes the response map so rule builders can populate shared metadata in a thread-safe manner.
	 * <p>
	 * 🧰 Expone el mapa de respuestas para que los constructores de reglas puedan rellenar metadata compartida de forma segura.
	 *
	 * @return the api response map / el mapa de respuestas API
	 * @author Aura Cristina Garzón Rodríguez (auragarzonr@gmail.com)
	 * @since Thursday 1 January 2026 8:01 AM GMT -5 Bogotá DC Colombia
	 */
	public Map<String, ApiResponse> getApiResponseMap() {
		return apiResponseMap;
	}
}
