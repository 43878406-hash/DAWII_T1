package com.cibertec.msriesgo.negocio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Prueba unitaria de la regla del 70% (no requiere base de datos ni RabbitMQ).
class AnalisisServiceTests {

	private final AnalisisService service = new AnalisisService(null);

	@Test
	void montoMenorAl70PorCientoEsAprobada() {
		assertEquals(AnalisisService.APROBADA,
				service.determinarSituacion(new BigDecimal("500.00"), new BigDecimal("1000.00")));
	}

	@Test
	void montoIgualAl70PorCientoEsAprobada() {
		assertEquals(AnalisisService.APROBADA,
				service.determinarSituacion(new BigDecimal("700.00"), new BigDecimal("1000.00")));
	}

	@Test
	void montoMayorAl70PorCientoEsObservada() {
		assertEquals(AnalisisService.OBSERVADA,
				service.determinarSituacion(new BigDecimal("700.01"), new BigDecimal("1000.00")));
	}
}
