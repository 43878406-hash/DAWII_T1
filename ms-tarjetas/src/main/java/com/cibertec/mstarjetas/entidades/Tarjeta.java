package com.cibertec.mstarjetas.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tarjetas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarjeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTarjeta;

	@Column(nullable = false, length = 120)
	private String nomTitular;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal saldoAsignado;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal saldoDisponible;
}
