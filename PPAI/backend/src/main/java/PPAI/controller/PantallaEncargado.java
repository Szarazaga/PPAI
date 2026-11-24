package PPAI.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import PPAI.dto.ConfirmarEnvioRequest;
import PPAI.dto.FiltroBolsinesRequest;
import PPAI.dto.SeleccionarBolsinRequest;
import PPAI.dto.UbicacionBolsinesResponse;
import PPAI.services.GestorDeConsultaBolsines;

@RestController
@RequestMapping("/api/bolsines")
@CrossOrigin(origins = "http://localhost:8081")
public class PantallaEncargado {

    private final GestorDeConsultaBolsines gestor;

    public PantallaEncargado(GestorDeConsultaBolsines gestor) {
        this.gestor = gestor;
    }

    private UbicacionBolsinesResponse habilitarPantalla() {
        return gestor.consultarUbicacionDeBolsines();
    }

    // Ejecuta del Paso 1 al Paso 6 de corrido
    @GetMapping("/consultar-ubicacion")
    public ResponseEntity<?> opcionConsultarUbicacionBolsines() {
        UbicacionBolsinesResponse respuesta = habilitarPantalla();

        // Flujo Alternativo A1: No se encuentran bolsines en estado enviado[cite: 2]
        if (respuesta.getUbicaciones() == null || respuesta.getUbicaciones().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "codigoCM", respuesta.getCodigoCM(),
                            "nombreCM", respuesta.getNombreCM(),
                            "error", "No se encuentran bolsines en estado enviado."
                    ));
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<?> tomarFiltroBolsines(
            @RequestParam(required = false) Integer numeroPrecinto,
            @RequestParam(required = false) String codigoCmDestino) {

        UbicacionBolsinesResponse respuesta = gestor.filtrarBolsines(numeroPrecinto, codigoCmDestino);

        // Flujo Alternativo A2: No se encuentra bolsín con el número de precinto ingresado
        if (respuesta.getUbicaciones() == null || respuesta.getUbicaciones().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "codigoCM", respuesta.getCodigoCM(),
                            "nombreCM", respuesta.getNombreCM(),
                            "error", "No se encuentra bolsín con el número de precinto o CM destino ingresado."
                    ));
        }

        return ResponseEntity.ok(respuesta);
    }


    // Paso 7 y 8: EB selecciona un bolsín del mapa -> Sistema consulta si desea enviar correo al GCM destino
    @PostMapping("/seleccionar-bolsin")
    public ResponseEntity<?> tomarSeleccionBolsin(@RequestParam int numeroBolsin) {
        try {
            gestor.tomarSeleccionBolsin(numeroBolsin);
            return ResponseEntity.ok(Map.of(
                    "numeroBolsin", numeroBolsin,
                    "mensajeConfirmacion", "¿Desea enviar un correo electrónico al Gerente de Comisión Médica destino para informar la ubicación del Bolsín?"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

// Paso 9, 10 y 11: EB confirma envío -> Busca correo GCM destino e incluye CU 31
    @PostMapping("/confirmar-envio-correo")
    public ResponseEntity<?> tomarConfirmacionEnvioCorreo(@RequestParam boolean confirmado) {
        // Flujo Alternativo A5: El EB no selecciona la opción para enviar un correo electrónico
        if (!confirmado) {
            return ResponseEntity.ok(Map.of(
                    "enviado", false,
                    "mensaje", "El envío de correo electrónico fue cancelado."
            ));
        }

        return ResponseEntity.ok(gestor.tomarConfirmacionEnvioCorreo());
    }

}
