package PPAI.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Sesion;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Date> {
    //Buscar sesion activa
    Sesion findFirstByFechaHoraFinIsNull();
}
