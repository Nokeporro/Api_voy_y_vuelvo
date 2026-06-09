package com.voy.vuelvo.ms_pago.controller;

import com.voy.vuelvo.ms_pago.model.Pago;
import com.voy.vuelvo.ms_pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "pagos", description = "Gestor de pagos para reservas de rutas de trekking")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Retorna el listado completo de pagos registrados en el sistema")
    public ResponseEntity<List<Pago>> getPagos() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Busca un pago por su ID, retorna 404 si no existe")
    public ResponseEntity<Pago> getPagoPorId(@PathVariable Long id) {
        try {
            Pago pago = pagoService.findById(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Crea un pago asociado a una reserva existente con su monto y estado")
    public ResponseEntity<Pago> createPago(@Valid @RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}