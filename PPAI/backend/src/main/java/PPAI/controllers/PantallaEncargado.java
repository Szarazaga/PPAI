package PPAI.controllers;

import PPAI.services.GestorDeConsultaBolsines;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bolsines")

public class PantallaEncargado {
    private final GestorDeConsultaBolsines gestorDeConsultaBolsines;

    public PantallaEncargado(GestorDeConsultaBolsines gestor){
        this.gestorDeConsultaBolsines = gestor;
    }
}
