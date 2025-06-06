package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.RegistroPuntos;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.RegistroPuntosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroPuntosService {

    @Autowired
    private RegistroPuntosRepository registroPuntosRepository;

    public RegistroPuntos registrarPuntos(RegistroPuntos registro) {
        return registroPuntosRepository.save(registro);
    }

    public List<RegistroPuntos> obtenerPuntosPorUsuario(Usuario usuario) {
        return registroPuntosRepository.findByUsuario(usuario);
    }

    public int calcularTotalPuntos(Usuario usuario) {
        return obtenerPuntosPorUsuario(usuario)
                .stream()
                .mapToInt(RegistroPuntos::getCantidad)
                .sum();
    }
}
