package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.RegistroPuntos;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.RegistroPuntosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistroPuntosServiceTest {

    @Mock
    private RegistroPuntosRepository registroPuntosRepository;

    @InjectMocks
    private RegistroPuntosService registroPuntosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarPuntos_GuardarRegistro() {
        RegistroPuntos registro = new RegistroPuntos();
        registro.setCantidad(150);

        when(registroPuntosRepository.save(any(RegistroPuntos.class))).thenReturn(registro);

        RegistroPuntos resultado = registroPuntosService.registrarPuntos(registro);

        assertNotNull(resultado);
        assertEquals(150, resultado.getCantidad());
        verify(registroPuntosRepository, times(1)).save(registro);
    }

    @Test
    void obtenerPuntosPorUsuario_RetornarListaCorrecta() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        RegistroPuntos r1 = new RegistroPuntos();
        RegistroPuntos r2 = new RegistroPuntos();
        List<RegistroPuntos> registros = Arrays.asList(r1, r2);

        when(registroPuntosRepository.findByUsuario(usuario)).thenReturn(registros);

        List<RegistroPuntos> resultado = registroPuntosService.obtenerPuntosPorUsuario(usuario);

        assertEquals(2, resultado.size());
        verify(registroPuntosRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    void calcularTotalPuntos_SumarCorrectamente() {
        Usuario usuario = new Usuario();

        RegistroPuntos r1 = new RegistroPuntos();
        r1.setCantidad(100);

        RegistroPuntos r2 = new RegistroPuntos();
        r2.setCantidad(50);

        when(registroPuntosRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(r1, r2));

        int total = registroPuntosService.calcularTotalPuntos(usuario);

        assertEquals(150, total);
        verify(registroPuntosRepository, times(1)).findByUsuario(usuario);
    }
}
