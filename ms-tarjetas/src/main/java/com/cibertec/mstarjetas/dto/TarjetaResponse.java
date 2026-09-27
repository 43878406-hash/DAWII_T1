package com.cibertec.mstarjetas.dto;

import java.math.BigDecimal;

public record TarjetaResponse(
		Long idTarjeta,
		String nomTitular,
		BigDecimal saldoAsignado,
		BigDecimal saldoDisponible
) {
}
