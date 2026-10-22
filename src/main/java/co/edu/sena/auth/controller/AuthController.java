package co.edu.sena.auth.controller;

import co.edu.sena.auth.dto.CredencialesDTO;
import co.edu.sena.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone los servicios web de registro e inicio de sesión de PequeGestión.
 * Recibe la petición HTTP y responde con el código de estado correspondiente.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // permite peticiones desde el front-end en React
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/registro
     * 201 si se registra · 409 si el usuario ya existe · 400 si faltan datos
     */
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@Valid @RequestBody CredencialesDTO datos) {
        if (!authService.registrar(datos)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
    }

    /**
     * POST /api/auth/login
     * 200 si las credenciales son correctas · 401 si no · 400 si faltan datos.
     * El mensaje de error es siempre el mismo para no dar pistas a un atacante.
     */
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(@Valid @RequestBody CredencialesDTO datos) {
        if (authService.autenticar(datos)) {
            return ResponseEntity.ok("Autenticación satisfactoria");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación");
    }
}