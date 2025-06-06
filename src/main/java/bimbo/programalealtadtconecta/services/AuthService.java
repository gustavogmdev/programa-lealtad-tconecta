package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.dto.AuthRequest;
import bimbo.programalealtadtconecta.dto.AuthResponse;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.UsuarioRepository;
import bimbo.programalealtadtconecta.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        String token = jwtUtil.generateToken(usuarioOpt.get().getUsername());
        return new AuthResponse(token);
    }
}
