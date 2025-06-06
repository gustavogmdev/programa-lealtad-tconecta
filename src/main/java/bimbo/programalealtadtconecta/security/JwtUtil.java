package bimbo.programalealtadtconecta.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final Algorithm algorithm;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getVerifier().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private JWTVerifier getVerifier() {
        return JWT.require(algorithm).build();
    }

    private DecodedJWT getDecodedJWT(String token) {
        return getVerifier().verify(token);
    }
}
