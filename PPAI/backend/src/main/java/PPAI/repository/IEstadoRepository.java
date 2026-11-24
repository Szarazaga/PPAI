package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Estado;

@Repository
public interface IEstadoRepository extends JpaRepository<Estado, String> {
}
