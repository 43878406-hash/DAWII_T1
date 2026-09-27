package com.cibertec.mstarjetas.config;

import com.cibertec.mstarjetas.entidades.Tarjeta;
import com.cibertec.mstarjetas.repositorio.TarjetaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class TarjetaDataInitializer {

	@Bean
	CommandLineRunner seedTarjetas(TarjetaRepository tarjetaRepository) {
		return args -> {
			if (tarjetaRepository.count() > 0) {
				return;
			}

			tarjetaRepository.save(Tarjeta.builder()
					.nomTitular("Juan Perez")
					.saldoAsignado(new BigDecimal("1000.00"))
					.saldoDisponible(new BigDecimal("1000.00"))
					.build());
			tarjetaRepository.save(Tarjeta.builder()
					.nomTitular("Maria Lopez")
					.saldoAsignado(new BigDecimal("500.00"))
					.saldoDisponible(new BigDecimal("350.00"))
					.build());
			tarjetaRepository.save(Tarjeta.builder()
					.nomTitular("Empresa Andina SAC")
					.saldoAsignado(new BigDecimal("5000.00"))
					.saldoDisponible(new BigDecimal("200.00"))
					.build());
		};
	}
}
