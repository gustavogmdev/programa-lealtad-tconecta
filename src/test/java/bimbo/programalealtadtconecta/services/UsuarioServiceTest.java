package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void registrarUsuario_GuardarUsuarioConPasswordEncriptado() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan");
        usuario.setPassword("1234");

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario registrado = usuarioService.registrarUsuario(usuario);

        assertNotNull(registrado);
        assertNotEquals("1234", registrado.getPassword());
        assertTrue(passwordEncoder.matches("1234", registrado.getPassword()));

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void buscarPorUsername_RetornarUsuarioSiExiste() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan");

        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("juan");

        assertTrue(resultado.isPresent());
        assertEquals("juan", resultado.get().getUsername());

        verify(usuarioRepository, times(1)).findByUsername("juan");
    }

    @Test
    void buscarPorId_RetornarUsuarioSiExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());

        verify(usuarioRepository, times(1)).findById(1L);
    }
}
