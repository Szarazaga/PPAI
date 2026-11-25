package ar.edu.dsi.gpstracker.repository;

import ar.edu.dsi.gpstracker.model.Bolsin;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryBolsinRepository {

    private final List<Bolsin> bolsines = new ArrayList<>();

    @PostConstruct
    void init() {
        bolsines.add(new Bolsin(101, "CM-CBA-01", "CM-ROS-02", -31.4201, -64.1888, LocalDateTime.of(2026, 3, 7, 12, 30, 0)));
        bolsines.add(new Bolsin(102, "CM-ROS-02", "CM-MDZ-03", -32.9468, -60.6393, LocalDateTime.of(2026, 3, 7, 12, 32, 15)));
        bolsines.add(new Bolsin(103, "CM-MDZ-03", "CM-BA-01", -32.8895, -68.8458, LocalDateTime.of(2026, 3, 7, 12, 35, 4)));
        bolsines.add(new Bolsin(104, "CM-BA-01", "CM-CBA-01", -34.6037, -58.3816, LocalDateTime.of(2026, 3, 7, 12, 36, 20)));
        bolsines.add(new Bolsin(105, "CM-TUC-01", "CM-SLA-01", -26.8083, -65.2176, LocalDateTime.of(2026, 3, 7, 12, 40, 31)));
        bolsines.add(new Bolsin(106, "CM-SLA-01", "CM-JUJ-01", -24.7829, -65.4232, LocalDateTime.of(2026, 3, 7, 12, 43, 18)));
        bolsines.add(new Bolsin(107, "CM-NQN-01", "CM-MDQ-01", -38.9516, -68.0591, LocalDateTime.of(2026, 3, 7, 12, 45, 46)));
        bolsines.add(new Bolsin(108, "CM-MDQ-01", "CM-BA-01", -38.0055, -57.5426, LocalDateTime.of(2026, 3, 7, 12, 47, 2)));
        bolsines.add(new Bolsin(109, "CM-LPL-01", "CM-CBA-01", -34.9215, -57.9545, LocalDateTime.of(2026, 3, 7, 12, 49, 27)));
        bolsines.add(new Bolsin(110, "CM-CBA-01", "CM-ROS-02", -31.4167, -64.1833, LocalDateTime.of(2026, 3, 7, 12, 52, 9)));
        bolsines.add(new Bolsin(111, "CM-CBA-01", "CM-JUJ-01", -29.4101, -65.1238, LocalDateTime.of(2026, 3, 7, 12, 30, 0)));
        bolsines.add(new Bolsin(112, "CM-CBA-01", "CM-SLA-01", -33.4801, -67.1854, LocalDateTime.of(2026, 3, 7, 12, 30, 0)));
        bolsines.add(new Bolsin(113, "CM-CBA-01", "CM-MDQ-01", -31.7801, -62.1854, LocalDateTime.of(2026, 3, 7, 12, 30, 0)));
        bolsines.add(new Bolsin(114, "CM-CBA-01", "CM-ROS-02", -32.4103, -63.2436, LocalDateTime.of(2026, 3, 7, 12, 55, 12)));
        bolsines.add(new Bolsin(115, "CM-CBA-01", "CM-MDZ-03", -33.2950, -66.3356, LocalDateTime.of(2026, 3, 7, 12, 58, 40)));
        bolsines.add(new Bolsin(116, "CM-CBA-01", "CM-BA-01", -33.3335, -60.2110, LocalDateTime.of(2026, 3, 7, 13, 1, 5)));
        bolsines.add(new Bolsin(117, "CM-CBA-01", "CM-TUC-01", -27.7834, -64.2642, LocalDateTime.of(2026, 3, 7, 13, 3, 50)));
        bolsines.add(new Bolsin(118, "CM-CBA-01", "CM-SLA-01", -25.8022, -64.9681, LocalDateTime.of(2026, 3, 7, 13, 6, 14)));
        bolsines.add(new Bolsin(119, "CM-CBA-01", "CM-JUJ-01", -24.3861, -65.2764, LocalDateTime.of(2026, 3, 7, 13, 9, 33)));
        bolsines.add(new Bolsin(120, "CM-CBA-01", "CM-NQN-01", -38.7183, -62.2663, LocalDateTime.of(2026, 3, 7, 13, 12, 48)));
        bolsines.add(new Bolsin(121, "CM-CBA-01", "CM-MDQ-01", -36.3132, -57.6792, LocalDateTime.of(2026, 3, 7, 13, 15, 22)));
        bolsines.add(new Bolsin(122, "CM-CBA-01", "CM-LPL-01", -34.7610, -58.2124, LocalDateTime.of(2026, 3, 7, 13, 18, 9)));
        bolsines.add(new Bolsin(123, "CM-CBA-01", "CM-ROS-02", -31.8524, -62.7145, LocalDateTime.of(2026, 3, 7, 13, 20, 45)));
        bolsines.add(new Bolsin(124, "CM-CBA-01", "CM-MDZ-03", -32.6841, -65.1289, LocalDateTime.of(2026, 3, 7, 13, 23, 11)));
        bolsines.add(new Bolsin(125, "CM-CBA-01", "CM-BA-01", -34.1205, -59.4521, LocalDateTime.of(2026, 3, 7, 13, 26, 37)));
        bolsines.add(new Bolsin(126, "CM-CBA-01", "CM-TUC-01", -29.9034, -63.7261, LocalDateTime.of(2026, 3, 7, 13, 29, 54)));
        bolsines.add(new Bolsin(127, "CM-CBA-01", "CM-LPL-01", -34.5122, -58.7812, LocalDateTime.of(2026, 3, 7, 13, 32, 19)));
        bolsines.add(new Bolsin(128, "CM-CBA-01", "CM-NQN-01", -35.6725, -63.7576, LocalDateTime.of(2026, 3, 7, 13, 35, 0)));
    }

    public List<Bolsin> findAll() {
        return List.copyOf(bolsines);
    }

    public Optional<Bolsin> findByNumeroBolsin(Integer numeroBolsin) {
        return bolsines.stream()
                .filter(b -> b.getNumeroBolsin().equals(numeroBolsin))
                .findFirst();
    }
}
