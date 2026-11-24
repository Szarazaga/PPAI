package PPAI.controller;

import PPAI.services.GestorDeConsultaBolsines;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consulta-bolsines")
@CrossOrigin(origins = "*")

public class PantallaEncargado {
    private final GestorDeConsultaBolsines gestor;

    public PantallaEncargado(GestorDeConsultaBolsines gestor){
        this.gestor = gestor;
    }
    @GetMapping("/iniciar")
    public List<String[]>habilitarPantalla(){
        return gestor.consultarUbicacionDeBolsines();
    }

}
