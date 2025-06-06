package bimbo.programalealtadtconecta.services;

import bimbo.programalealtadtconecta.models.Canje;
import bimbo.programalealtadtconecta.models.Usuario;
import bimbo.programalealtadtconecta.repositories.CanjeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CanjeServiceTest {

    @Mock
    private CanjeRepository canjeRepository;

    @InjectMocks
    private CanjeService canjeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCanje_GuardarCanje() {
        Canje canje = new Canje();
        canje.setPuntosUtilizados(100);

        when(canjeRepository.save(any(Canje.class))).thenReturn(canje);

        Canje resultado = canjeService.registrarCanje(canje);

        assertNotNull(resultado);
        assertEquals(100, resultado.getPuntosUtilizados());
        verify(canjeRepository, times(1)).save(canje);
    }

    @Test
    void obtenerCanjesPorUsuario_RetornarListaCorrecta() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Canje c1 = new Canje();
        Canje c2 = new Canje();
        List<Canje> canjes = Arrays.asList(c1, c2);

        when(canjeRepository.findByUsuario(usuario)).thenReturn(canjes);

        List<Canje> resultado = canjeService.obtenerCanjesPorUsuario(usuario);

        assertEquals(2, resultado.size());
        verify(canjeRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    void totalPuntosCanjeados_SumarPuntosCorrectamente() {
        Usuario usuario = new Usuario();

        Canje c1 = new Canje();
        c1.setPuntosUtilizados(100);

        Canje c2 = new Canje();
        c2.setPuntosUtilizados(150);

        when(canjeRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(c1, c2));

        int total = canjeService.totalPuntosCanjeados(usuario);

        assertEquals(250, total);
        verify(canjeRepository, times(1)).findByUsuario(usuario);
    }
}
