package com.cibertec.msrecargas.rest;

import com.cibertec.msrecargas.dto.RecargaRequest;
import com.cibertec.msrecargas.dto.RecargaResponse;
import com.cibertec.msrecargas.negocio.RecargaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recargas")
public class RecargaController {

	private final RecargaService recargaService;

	public RecargaController(RecargaService recargaService) {
		this.recargaService = recargaService;
	}

	@GetMapping
	public ResponseEntity<List<RecargaResponse>> getAll() {
		return ResponseEntity.ok(recargaService.getAllRecargas());
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecargaResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(recargaService.getRecargaById(id));
	}

	@PostMapping
	public ResponseEntity<RecargaResponse> create(@RequestBody RecargaRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(recargaService.createRecarga(request));
	}
}
