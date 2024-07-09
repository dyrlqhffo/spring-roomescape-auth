package roomescape.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtTokenProvider {

    private SecretKey secretKey;
    private long validityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.expire}") long validityInMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(secretKey)
                .compact();
    }

    public String getEmailByToken(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

}