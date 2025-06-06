package bimbo.programalealtadtconecta.controllers;

import bimbo.programalealtadtconecta.models.Recompensa;
import bimbo.programalealtadtconecta.services.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
public class RecompensaController {

    @Autowired
    private RecompensaService recompensaService;

    @GetMapping
    public List<Recompensa> listar() {
        return recompensaService.listarRecompensas();
    }

    @PostMapping
    public Recompensa crear(@RequestBody Recompensa recompensa) {
        return recompensaService.crearRecompensa(recompensa);
    }
}
