package com.cibertec.msrecargas.negocio;

import com.cibertec.msrecargas.client.TarjetaClient;
import com.cibertec.msrecargas.dto.RecargaRequest;
import com.cibertec.msrecargas.dto.RecargaResponse;
import com.cibertec.msrecargas.dto.TarjetaResponse;
import com.cibertec.msrecargas.entidades.Recarga;
import com.cibertec.msrecargas.rabbitmq.RecargaProducer;
import com.cibertec.msrecargas.rabbitmq.RecargaRegistradaEvent;
import com.cibertec.msrecargas.repositorio.RecargaRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecargaService {

	private final RecargaRepository recargaRepository;
	private final TarjetaClient tarjetaClient;
	private final RecargaProducer recargaProducer;

	public RecargaService(
			RecargaRepository recargaRepository,
			TarjetaClient tarjetaClient,
			RecargaProducer recargaProducer
	) {
		this.recargaRepository = recargaRepository;
		this.tarjetaClient = tarjetaClient;
		this.recargaProducer = recargaProducer;
	}

	public List<RecargaResponse> getAllRecargas() {
		return recargaRepository.findAll().stream()
				.map(this::buildRecargaResponse)
				.toList();
	}

	public RecargaResponse getRecargaById(Long idRecarga) {
		Recarga recarga = recargaRepository.findById(idRecarga)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recarga no encontrada"));

		return buildRecargaResponse(recarga);
	}

	public RecargaResponse createRecarga(RecargaRequest request) {
		if (request.idTarjeta() == null || request.montoRecarga() == null
				|| request.montoRecarga().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"idTarjeta y montoRecarga (mayor a 0) son obligatorios");
		}

		TarjetaResponse tarjeta = obtenerTarjeta(request.idTarjeta());

		Recarga recarga = Recarga.builder()
				.idTarjeta(tarjeta.idTarjeta())
				.saldoDisponible(tarjeta.saldoDisponible())
				.montoRecarga(request.montoRecarga())
				.fechaRecarga(LocalDateTime.now())
				.build();
		Recarga storedRecarga = recargaRepository.save(recarga);

		recargaProducer.publish(new RecargaRegistradaEvent(
				storedRecarga.getIdRecarga(),
				storedRecarga.getIdTarjeta(),
				storedRecarga.getSaldoDisponible(),
				storedRecarga.getMontoRecarga(),
				storedRecarga.getFechaRecarga()
		));

		return buildRecargaResponse(storedRecarga);
	}

	private TarjetaResponse obtenerTarjeta(Long idTarjeta) {
		try {
			return tarjetaClient.getTarjetaById(idTarjeta);
		} catch (FeignException.NotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"La tarjeta " + idTarjeta + " no existe. No se registró la recarga");
		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-tarjetas no disponible");
		}
	}

	private RecargaResponse buildRecargaResponse(Recarga recarga) {
		return new RecargaResponse(
				recarga.getIdRecarga(),
				recarga.getIdTarjeta(),
				recarga.getSaldoDisponible(),
				recarga.getMontoRecarga(),
				recarga.getFechaRecarga()
		);
	}
}
