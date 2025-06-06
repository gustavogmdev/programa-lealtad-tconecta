package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.Accion;
import bimbo.programalealtadtconecta.repositories.AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccionService {

    @Autowired
    private AccionRepository accionRepository;

    public List<Accion> listarAcciones() {
        return accionRepository.findAll();
    }

    public Accion crearAccion(Accion accion) {
        return accionRepository.save(accion);
    }

    public Accion obtenerPorId(Long id) {
        return accionRepository.findById(id).orElse(null);
    }
}
