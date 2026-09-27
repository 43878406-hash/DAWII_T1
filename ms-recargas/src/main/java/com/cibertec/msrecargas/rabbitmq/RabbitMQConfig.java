package com.cibertec.msrecargas.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String RECARGAS_EXCHANGE = "recargas-exchange";
	public static final String RECARGAS_QUEUE = "Yupanqui_Queue";
	public static final String RECARGAS_ROUTING_KEY = "recarga.registrada";

	@Bean
	public DirectExchange recargasExchange() {
		return new DirectExchange(RECARGAS_EXCHANGE);
	}

	@Bean
	public Queue recargasQueue() {
		return new Queue(RECARGAS_QUEUE);
	}

	@Bean
	public Binding recargasBinding(Queue recargasQueue, DirectExchange recargasExchange) {
		return BindingBuilder.bind(recargasQueue)
				.to(recargasExchange)
				.with(RECARGAS_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}
}
