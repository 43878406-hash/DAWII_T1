package com.cibertec.msriesgo.negocio;

import com.cibertec.msriesgo.entidades.Analisis;
import com.cibertec.msriesgo.rabbitmq.RecargaRegistradaEvent;
import com.cibertec.msriesgo.repositorio.AnalisisRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalisisService {

	public static final String APROBADA = "Aprobada";
	public static final String OBSERVADA = "Observada";
	private static final BigDecimal PORCENTAJE_LIMITE = new BigDecimal("0.70");

	private final AnalisisRepository analisisRepository;

	public AnalisisService(AnalisisRepository analisisRepository) {
		this.analisisRepository = analisisRepository;
	}

	public List<Analisis> getAllAnalisis() {
		return analisisRepository.findAll();
	}

	public Analisis registrarAnalisis(RecargaRegistradaEvent event) {
		Analisis analisis = Analisis.builder()
				.idRecarga(event.idRecarga())
				.idTarjeta(event.idTarjeta())
				.saldoDisponible(event.saldoDisponible())
				.montoRecarga(event.montoRecarga())
				.fechaRecarga(event.fechaRecarga() != null ? event.fechaRecarga() : LocalDateTime.now())
				.situacion(determinarSituacion(event.montoRecarga(), event.saldoDisponible()))
				.build();

		return analisisRepository.save(analisis);
	}

	String determinarSituacion(BigDecimal montoRecarga, BigDecimal saldoDisponible) {
		BigDecimal limite = saldoDisponible.multiply(PORCENTAJE_LIMITE);
		return montoRecarga.compareTo(limite) <= 0 ? APROBADA : OBSERVADA;
	}
}
