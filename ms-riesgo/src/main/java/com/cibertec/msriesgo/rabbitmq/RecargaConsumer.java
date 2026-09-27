package com.cibertec.msriesgo.rabbitmq;

import com.cibertec.msriesgo.entidades.Analisis;
import com.cibertec.msriesgo.negocio.AnalisisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RecargaConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecargaConsumer.class);

	private final AnalisisService analisisService;

	public RecargaConsumer(AnalisisService analisisService) {
		this.analisisService = analisisService;
	}

	@RabbitListener(queues = RabbitMQConfig.RECARGAS_QUEUE)
	public void onRecargaRegistrada(RecargaRegistradaEvent event) {
		LOGGER.info("[ms-riesgo] Mensaje recibido de {}: {}", RabbitMQConfig.RECARGAS_QUEUE, event);
		Analisis analisis = analisisService.registrarAnalisis(event);
		LOGGER.info("[ms-riesgo] Analisis registrado. idRecarga={}, idTarjeta={}, monto={}, saldo={}, situacion={}",
				analisis.getIdRecarga(), analisis.getIdTarjeta(), analisis.getMontoRecarga(),
				analisis.getSaldoDisponible(), analisis.getSituacion());
	}
}
