package bimbo.programalealtadtconecta.controllers;

import bimbo.programalealtadtconecta.dto.UsuarioResponse;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.services.CanjeService;
import bimbo.programalealtadtconecta.services.RegistroPuntosService;
import bimbo.programalealtadtconecta.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistroPuntosService registroPuntosService;

    @Autowired
    private CanjeService canjeService;

    @PostMapping("/registrar")
    public UsuarioResponse registrarUsuario(@RequestBody Usuario usuario) {
        Usuario registrado = usuarioService.registrarUsuario(usuario);
        return new UsuarioResponse(registrado.getId(), registrado.getUsername());
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/saldo")
    public int obtenerSaldo() {
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        Usuario usuario = usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        int acumulados = registroPuntosService.calcularTotalPuntos(usuario);
        int canjeados = canjeService.totalPuntosCanjeados(usuario);
        return acumulados - canjeados;
    }

}
