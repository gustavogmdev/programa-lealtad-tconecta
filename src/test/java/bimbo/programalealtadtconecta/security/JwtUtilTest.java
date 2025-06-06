package bimbo.programalealtadtconecta.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secret = "T9YpN3vUeXz6mCkR7qL2bVtA8sDfJwGh";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret);
    }

    @Test
    void generateToken_CrearTokenValido() {
        String token = jwtUtil.generateToken("juan");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_RetornarElUsername() {
        String token = jwtUtil.generateToken("maria");
        String username = jwtUtil.extractUsername(token);
        assertEquals("maria", username);
    }

    @Test
    void validateToken_RetornarTrueSiEsValido() {
        String token = jwtUtil.generateToken("admin");
        boolean valido = jwtUtil.validateToken(token);
        assertTrue(valido);
    }

    @Test
    void validateToken_RetornarFalseSiEsInvalido() {
        String tokenInvalido = "token.que.no.es.valido";
        boolean valido = jwtUtil.validateToken(tokenInvalido);
        assertFalse(valido);
    }
}
