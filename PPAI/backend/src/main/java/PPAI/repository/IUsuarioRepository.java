package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByNombreAndContrasena(String nombre, String contrasena);
}
