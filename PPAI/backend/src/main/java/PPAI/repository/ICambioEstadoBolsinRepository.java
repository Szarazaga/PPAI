package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.CambioEstadoBolsin;

import java.util.Date;

@Repository
public interface ICambioEstadoBolsinRepository extends JpaRepository<CambioEstadoBolsin, Date> {
}
