package team20.issuetracker.login.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import team20.issuetracker.exception.MyJwtException;

@Component
public class JwtTokenProvider {

    public static final String TOKEN_TYPE = "Bearer";
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final String secretKey;

    public JwtTokenProvider(@Value("${jwt.accessTokenExpiry}") long accessTokenValidityInMilliseconds,
                            @Value("${jwt.refreshTokenExpiry}") long refreshTokenValidityInMilliseconds,
                            @Value("${jwt.secretKey}") String secretKey) {
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        this.secretKey = secretKey;
    }

    public String createAccessToken(String payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        return createToken(generatedString, refreshTokenValidityInMilliseconds);
    }

    public String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            validateTokeType(token);
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (IllegalArgumentException | ExpiredJwtException e) {
            throw new MyJwtException("유효하지 않은 토큰 입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    private void validateTokeType(String token) {
        String tokenType = token.split(" ")[0];
        if (!tokenType.equals(TOKEN_TYPE)) {
            throw new MyJwtException("토큰 타입이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
