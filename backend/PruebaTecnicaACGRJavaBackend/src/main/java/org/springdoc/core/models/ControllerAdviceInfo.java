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
 * The type Controller advice info.
 * @author bnasslahsen
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
	 * Instantiates a new Controller advice info.
	 *
	 * @param beanName         the bean name
	 * @param controllerAdvice the controller advice
	 */
	public ControllerAdviceInfo(String beanName, Object controllerAdvice) {
		this.beanName = beanName;
		this.controllerAdvice = controllerAdvice;
		this.controllerAdviceAnnotation = AnnotatedElementUtils.findMergedAnnotation(controllerAdvice.getClass(), ControllerAdvice.class);
	}

	/**
	 * Gets controller advice.
	 *
	 * @return the controller advice
	 */
	public Object getControllerAdvice() {
		return controllerAdvice;
	}

	/**
	 * Gets bean name.
	 *
	 * @return the bean name
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * Gets controller advice annotation.
	 *
	 * @return the controller advice annotation
	 */
	public ControllerAdvice getControllerAdviceAnnotation() {
		return controllerAdviceAnnotation;
	}

	/**
	 * Gets api response map.
	 *
	 * @return the api response map
	 */
	public Map<String, ApiResponse> getApiResponseMap() {
		return apiResponseMap;
	}
}
