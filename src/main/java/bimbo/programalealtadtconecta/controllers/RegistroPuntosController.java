package bimbo.programalealtadtconecta.controllers;

import bimbo.programalealtadtconecta.dto.RegistroPuntosRequest;
import bimbo.programalealtadtconecta.models.RegistroPuntos;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.models.Accion;
import bimbo.programalealtadtconecta.services.AccionService;
import bimbo.programalealtadtconecta.services.RegistroPuntosService;
import bimbo.programalealtadtconecta.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/puntos")
public class RegistroPuntosController {

    @Autowired
    private RegistroPuntosService registroPuntosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AccionService accionService;

    @PostMapping("/registrar")
    public RegistroPuntos registrarPuntos(@RequestBody RegistroPuntosRequest request) {
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        Usuario usuario = usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Accion accion = accionService.obtenerPorId(request.getAccionId());
        if (accion == null) {
            throw new RuntimeException("Acción no encontrada");
        }

        RegistroPuntos registro = new RegistroPuntos();
        registro.setUsuario(usuario);
        registro.setAccion(accion);
        registro.setCantidad(accion.getPuntos());
        registro.setFecha(LocalDateTime.now());

        return registroPuntosService.registrarPuntos(registro);
    }
}
