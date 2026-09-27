package com.cibertec.mstarjetas.negocio;

import com.cibertec.mstarjetas.dto.TarjetaResponse;
import com.cibertec.mstarjetas.entidades.Tarjeta;
import com.cibertec.mstarjetas.repositorio.TarjetaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TarjetaService {

	private final TarjetaRepository tarjetaRepository;

	public TarjetaService(TarjetaRepository tarjetaRepository) {
		this.tarjetaRepository = tarjetaRepository;
	}

	public List<TarjetaResponse> getAllTarjetas() {
		return tarjetaRepository.findAll().stream()
				.map(this::mapToResponse)
				.toList();
	}

	public TarjetaResponse getTarjetaById(Long id) {
		Tarjeta tarjeta = tarjetaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));

		return mapToResponse(tarjeta);
	}

	public TarjetaResponse createTarjeta(Tarjeta tarjeta) {
		Tarjeta nuevaTarjeta = Tarjeta.builder()
				.nomTitular(tarjeta.getNomTitular())
				.saldoAsignado(tarjeta.getSaldoAsignado())
				// Si no se envía saldo disponible, la tarjeta inicia con todo su saldo asignado.
				.saldoDisponible(tarjeta.getSaldoDisponible() != null
						? tarjeta.getSaldoDisponible()
						: tarjeta.getSaldoAsignado())
				.build();

		return mapToResponse(tarjetaRepository.save(nuevaTarjeta));
	}

	private TarjetaResponse mapToResponse(Tarjeta tarjeta) {
		return new TarjetaResponse(
				tarjeta.getIdTarjeta(),
				tarjeta.getNomTitular(),
				tarjeta.getSaldoAsignado(),
				tarjeta.getSaldoDisponible()
		);
	}
}
