package com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTemplateConfig {

	/**
	 * Bean do {@link Jackson2JsonMessageConverter} para converter automaticamente
	 * objetos Java em JSON e vice-versa ao enviar ou receber mensagens pelo
	 * RabbitMQ.
	 *
	 * @return conversor JSON
	 */
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * Bean do {@link RabbitTemplate} configurado para usar conversão JSON.
	 * <p>
	 * O {@link RabbitTemplate} é a interface principal para envio de mensagens ao
	 * RabbitMQ. Configurando o {@link Jackson2JsonMessageConverter}, podemos enviar
	 * objetos Java diretamente sem precisar de serialização manual.
	 * </p>
	 *
	 * @param connectionFactory
	 *            conexão com o RabbitMQ
	 * @param messageConverter
	 *            conversor de mensagens JSON
	 * @return {@link RabbitTemplate} configurado para JSON
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		return template;
	}
}
