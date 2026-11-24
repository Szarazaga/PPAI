package PPAI.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import PPAI.model.Bolsin;

@Repository
public interface IBolsinRepository extends JpaRepository<Bolsin, Integer> {
    List<Bolsin> findByOrigenCodigo(String codigo);
}