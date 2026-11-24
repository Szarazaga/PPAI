package PPAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, String> {
}
