package team20.issuetracker.util;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

    public static Claims decodingToken(String token, String key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String tokenExtraction(HttpServletRequest request, String authorization) {
        return request.getHeader(authorization)
                .split(" ")[1];
    }
}
