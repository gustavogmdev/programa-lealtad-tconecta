package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.Canje;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.CanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class CanjeService {

    @Autowired
    private CanjeRepository canjeRepository;

    @Transactional
    public Canje registrarCanje(Canje canje) {
        return canjeRepository.save(canje);
    }

    public List<Canje> obtenerCanjesPorUsuario(Usuario usuario) {
        return canjeRepository.findByUsuario(usuario);
    }

    public int totalPuntosCanjeados(Usuario usuario) {
        return obtenerCanjesPorUsuario(usuario)
                .stream()
                .mapToInt(Canje::getPuntosUtilizados)
                .sum();
    }
}
