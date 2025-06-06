package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.dto.AuthRequest;
import bimbo.programalealtadtconecta.dto.AuthResponse;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.UsuarioRepository;
import bimbo.programalealtadtconecta.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_UsuarioYContrasenaCorrectos() {
        AuthRequest request = new AuthRequest();
        request.setUsername("juan");
        request.setPassword("123456");

        Usuario usuario = new Usuario();
        usuario.setUsername("juan");
        usuario.setPassword("encrypted");

        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123456", "encrypted")).thenReturn(true);
        when(jwtUtil.generateToken("juan")).thenReturn("fake-jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
    }

    @Test
    public void testLogin_UsuarioNoExiste() {
        AuthRequest request = new AuthRequest();
        request.setUsername("noexiste");
        request.setPassword("1234");

        when(usuarioRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Usuario o contraseña incorrectos", ex.getMessage());
    }

    @Test
    public void testLogin_ContrasenaIncorrecta() {
        AuthRequest request = new AuthRequest();
        request.setUsername("juan");
        request.setPassword("mala");

        Usuario usuario = new Usuario();
        usuario.setUsername("juan");
        usuario.setPassword("encrypted");

        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("mala", "encrypted")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Usuario o contraseña incorrectos", ex.getMessage());
    }
}
