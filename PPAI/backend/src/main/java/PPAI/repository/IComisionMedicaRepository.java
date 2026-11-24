package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.ComisionMedica;

@Repository
public interface IComisionMedicaRepository extends JpaRepository<ComisionMedica, String> {
}
