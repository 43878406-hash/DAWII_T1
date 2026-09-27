package com.cibertec.msrecargas.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RecargaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecargaProducer.class);

	private final RabbitTemplate rabbitTemplate;

	public RecargaProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publish(RecargaRegistradaEvent event) {
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.RECARGAS_EXCHANGE,
				RabbitMQConfig.RECARGAS_ROUTING_KEY,
				event
		);
		LOGGER.info("[ms-recargas] Recarga publicada en RabbitMQ. exchange={}, routingKey={}, queue={}, payload={}",
				RabbitMQConfig.RECARGAS_EXCHANGE, RabbitMQConfig.RECARGAS_ROUTING_KEY,
				RabbitMQConfig.RECARGAS_QUEUE, event);
	}
}
