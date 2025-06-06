package bimbo.programalealtadtconecta.repositories;

import bimbo.programalealtadtconecta.models.Canje;
import bimbo.programalealtadtconecta.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CanjeRepository extends JpaRepository<Canje, Long> {
    List<Canje> findByUsuario(Usuario usuario);
}
