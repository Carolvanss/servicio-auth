package co.edu.sena.auth.repository;

import co.edu.sena.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acceso a la tabla usuarios de PequeGestión.
 * Spring genera las consultas a partir del nombre de cada método.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Indica si ya existe un usuario con ese nombre
    boolean existsByNombreUsuario(String nombreUsuario);

    // Busca un usuario por su nombre
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}