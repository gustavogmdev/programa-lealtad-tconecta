package bimbo.programalealtadtconecta.controllers;

import bimbo.programalealtadtconecta.models.Accion;
import bimbo.programalealtadtconecta.services.AccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acciones")
public class AccionController {

    @Autowired
    private AccionService accionService;

    @GetMapping
    public List<Accion> listarAcciones() {
        return accionService.listarAcciones();
    }

    @PostMapping
    public Accion crearAccion(@RequestBody Accion accion) {
        return accionService.crearAccion(accion);
    }
}
