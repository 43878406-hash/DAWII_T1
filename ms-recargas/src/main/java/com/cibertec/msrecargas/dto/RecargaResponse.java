package com.cibertec.msrecargas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecargaResponse(
		Long idRecarga,
		Long idTarjeta,
		BigDecimal saldoDisponible,
		BigDecimal montoRecarga,
		LocalDateTime fechaRecarga
) {
}
