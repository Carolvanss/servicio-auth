package co.edu.sena.auth.service;

import co.edu.sena.auth.dto.CredencialesDTO;
import co.edu.sena.auth.model.Usuario;
import co.edu.sena.auth.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Lógica de registro e inicio de sesión de PequeGestión.
 * Es reutilizable: el controlador solo llama a estos métodos.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    // Encriptador BCrypt: convierte la contraseña en un texto ilegible
    private final BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un usuario nuevo.
     * @return false si el nombre de usuario ya existe, true si se registró
     */
    public boolean registrar(CredencialesDTO datos) {
        String nombreUsuario = datos.getUsuario().trim();

        // Validación: no se permiten usuarios repetidos
        if (usuarioRepository.existsByNombreUsuario(nombreUsuario)) {
            return false;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombreUsuario(nombreUsuario);
        // La contraseña se guarda protegida, nunca en texto plano
        nuevo.setContrasena(encriptador.encode(datos.getContrasena()));
        usuarioRepository.save(nuevo);
        return true;
    }

    /**
     * Verifica usuario y contraseña.
     * @return true si las credenciales son correctas, false en cualquier otro caso
     */
    public boolean autenticar(CredencialesDTO datos) {
        return usuarioRepository.findByNombreUsuario(datos.getUsuario().trim())
                // Compara la contraseña escrita con la guardada (encriptada)
                .map(u -> encriptador.matches(datos.getContrasena(), u.getContrasena()))
                // Si el usuario no existe, tampoco se autentica
                .orElse(false);
    }
}