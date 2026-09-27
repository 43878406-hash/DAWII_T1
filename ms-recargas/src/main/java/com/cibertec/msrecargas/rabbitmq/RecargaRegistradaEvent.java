package com.cibertec.msrecargas.rabbitmq;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecargaRegistradaEvent(
		Long idRecarga,
		Long idTarjeta,
		BigDecimal saldoDisponible,
		BigDecimal montoRecarga,
		LocalDateTime fechaRecarga
) {
}
