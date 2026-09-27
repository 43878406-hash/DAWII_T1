package com.cibertec.msriesgo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "analisis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analisis {

	@Id
	private Long idRecarga;

	@Column(nullable = false)
	private Long idTarjeta;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal saldoDisponible;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal montoRecarga;

	@Column(nullable = false)
	private LocalDateTime fechaRecarga;

	@Column(nullable = false, length = 20)
	private String situacion;
}
