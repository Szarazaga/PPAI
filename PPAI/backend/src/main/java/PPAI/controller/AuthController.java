package PPAI.controller;

import java.util.Date;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import PPAI.model.*;
import PPAI.dto.LoginRequest;
import PPAI.repository.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IUsuarioRepository usuarioRepository;
    private final ISesionRepository sesionRepository;
    private final IEmpleadoRepository empleadoRepository;

    public AuthController(
            IUsuarioRepository usuarioRepository,
            ISesionRepository sesionRepository,
            IEmpleadoRepository empleadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Validar credenciales en H2
        Usuario usuario = usuarioRepository.findByNombreAndContrasena(
                request.getNombre(), request.getContrasena()
        );

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Usuario o contraseña incorrectos"));
        }

        // 2. Si había una sesión activa previa, la cerramos
        Sesion sesionAnterior = sesionRepository.findFirstByFechaHoraFinIsNull();
        if (sesionAnterior != null) {
            sesionAnterior.finalizar(new Date());
            sesionRepository.save(sesionAnterior);
        }

        // 3. Creamos y persistimos la nueva sesión activa (fechaHoraFin en null)
        Sesion nuevaSesion = new Sesion(new Date(), null, usuario);
        sesionRepository.save(nuevaSesion);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Sesion sesionActiva = sesionRepository.findFirstByFechaHoraFinIsNull();
        if (sesionActiva != null) {
            sesionActiva.finalizar(new Date());
            sesionRepository.save(sesionActiva);
        }
        return ResponseEntity.ok(Map.of("mensaje", "Sesión cerrada"));
    }
}
