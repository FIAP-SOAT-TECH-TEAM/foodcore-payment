package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Configuração para adicionar suporte ao media type
 * {@code application/octet-stream} no
 * {@link MappingJackson2HttpMessageConverter}.
 *
 * <p>
 * Isso evita erros ao utilizar outros media types que não são suportados por
 * padrão, especialmente no contexto do Swagger UI, onde pode ocorrer problema
 * ao enviar arquivos ou dados binários com media type
 * "application/octet-stream".
 * </p>
 *
 * <p>
 * Baseado na discussão e solução sugerida na issue do Swagger UI: <a href=
 * "https://github.com/swagger-api/swagger-ui/issues/6462#issuecomment-1069115246">
 * https://github.com/swagger-api/swagger-ui/issues/6462#issuecomment-1069115246
 * </a>
 * </p>
 */
@Configuration
public class SwaggerBeanConfig {

	public SwaggerBeanConfig(
			@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter converter) {
		var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
		supportedMediaTypes.add(new MediaType("application", "octet-stream"));
		converter.setSupportedMediaTypes(supportedMediaTypes);
	}
}
