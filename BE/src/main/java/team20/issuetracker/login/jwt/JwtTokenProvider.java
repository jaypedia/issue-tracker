package team20.issuetracker.login.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.exception.MyJwtException;
import team20.issuetracker.util.JwtUtils;

@Component
public class JwtTokenProvider {

    public static final String TOKEN_TYPE = "Bearer";
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final String secretKey;
    private final String refreshSecretKey;

    public JwtTokenProvider(@Value("${jwt.accessTokenExpiry}") long accessTokenValidityInMilliseconds,
                            @Value("${jwt.refreshTokenExpiry}") long refreshTokenValidityInMilliseconds,
                            @Value("${jwt.secretKey}") String secretKey,
                            @Value("${jwt.refreshSecretKey}") String refreshSecretKey) {
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        this.secretKey = secretKey;
        this.refreshSecretKey = refreshSecretKey;
    }

    public String getAccessToken(Member member) {
        return createToken(member.getOauthId(),
                this.accessTokenValidityInMilliseconds,
                this.secretKey);
    }

    public String getAccessToken(String refreshToken) {
        String jwtId = JwtUtils.decodingToken(refreshToken, this.refreshSecretKey).getId();
        return createToken(jwtId,
                this.accessTokenValidityInMilliseconds,
                this.secretKey);
    }

    public String getRefreshToken(Member member) {
        return createToken(member.getOauthId(),
                this.refreshTokenValidityInMilliseconds,
                this.refreshSecretKey);
    }

    private String createToken(String jwtId,
                               long accessTokenValidityInMilliseconds,
                               String secretKey) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        Claims claims = Jwts.claims()
                .setId(jwtId)
                .setIssuedAt(now)
                .setExpiration(validity);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            throw new MyJwtException("서명을 확인할 수 없는 토큰입니다.", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new MyJwtException("잘못 구성된 토큰이 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            throw new MyJwtException("만료된 토큰 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new MyJwtException("형식에 맞지 않는 토큰 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new MyJwtException("유효하지 않은 토큰 입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    public String validateTokeType(String token) {
        String tokenType = token.split(" ")[0];
        if (!tokenType.equals(TOKEN_TYPE)) {
            throw new MyJwtException("토큰 타입이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return token.split(" ")[1];
    }

    public long getRefreshTokenValidityInMilliseconds() {
        return refreshTokenValidityInMilliseconds;
    }
}
