package bimbo.programalealtadtconecta.repositories;

import bimbo.programalealtadtconecta.models.RegistroPuntos;
import bimbo.programalealtadtconecta.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPuntosRepository extends JpaRepository<RegistroPuntos, Long> {
    List<RegistroPuntos> findByUsuario(Usuario usuario);
}
