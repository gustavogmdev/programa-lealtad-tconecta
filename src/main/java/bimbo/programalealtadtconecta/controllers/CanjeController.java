package bimbo.programalealtadtconecta.controllers;

import bimbo.programalealtadtconecta.models.Canje;
import bimbo.programalealtadtconecta.models.Recompensa;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.services.CanjeService;
import bimbo.programalealtadtconecta.services.RecompensaService;
import bimbo.programalealtadtconecta.services.RegistroPuntosService;
import bimbo.programalealtadtconecta.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canjes")
public class CanjeController {

    @Autowired
    private CanjeService canjeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecompensaService recompensaService;

    @Autowired
    private RegistroPuntosService registroPuntosService;

    @PostMapping
    public String canjear(@RequestParam Long recompensaId) {
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        Usuario usuario = usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Recompensa recompensa = recompensaService.obtenerPorId(recompensaId);
        if (recompensa == null) {
            return "Recompensa no encontrada.";
        }

        int acumulados = registroPuntosService.calcularTotalPuntos(usuario);
        int canjeados = canjeService.totalPuntosCanjeados(usuario);
        int saldo = acumulados - canjeados;

        if (saldo < recompensa.getValorEnPuntos()) {
            return "Saldo insuficiente para canjear esta recompensa.";
        }

        Canje canje = new Canje();
        canje.setUsuario(usuario);
        canje.setRecompensa(recompensa);
        canje.setPuntosUtilizados(recompensa.getValorEnPuntos());
        canjeService.registrarCanje(canje);

        return "Recompensa canjeada con éxito.";
    }


    @GetMapping("/mios")
    public List<Canje> listarMisCanjes() {
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        Usuario usuario = usuarioService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return canjeService.obtenerCanjesPorUsuario(usuario);
    }

}
