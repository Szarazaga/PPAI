package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Empleado;
import PPAI.model.EmpleadoId;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, EmpleadoId> {
    Empleado findByUsuarioNombre(String nombre);
}
