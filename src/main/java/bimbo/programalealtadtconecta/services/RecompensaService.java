package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.Recompensa;
import bimbo.programalealtadtconecta.repositories.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecompensaService {

    @Autowired
    private RecompensaRepository recompensaRepository;

    public List<Recompensa> listarRecompensas() {
        return recompensaRepository.findAll();
    }

    public Recompensa obtenerPorId(Long id) {
        return recompensaRepository.findById(id).orElse(null);
    }

    public Recompensa crearRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }
}
