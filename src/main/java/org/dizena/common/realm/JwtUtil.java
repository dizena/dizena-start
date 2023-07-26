package org.dizena.common.realm;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtUtil
{
    //jwt令牌的秘钥
    private static final String SECRET = "RElaRU5BSkFWQUFOREdPU0VDUkU=";
    //jwt的有限时间10年
    private static final long VALID_PERIOD = 1000L * 3600 * 24;

    public static String sign(String account, String password)
    {
        Date endDate = new Date(System.currentTimeMillis() + VALID_PERIOD);
        return Jwts.builder().setExpiration(endDate).setIssuer(account).setSubject(password).signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public static String getAccount(String token)
    {
        JwtParser jwtParser = Jwts.parser();
        byte[] bs = Base64.getDecoder().decode(SECRET);
        Jws<Claims> claimsJws = jwtParser.setSigningKey(bs).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.getIssuer();
    }

    public static boolean verify(String token, String account, String password)
    {
        JwtParser jwtParser = Jwts.parser();
        byte[] bs = Base64.getDecoder().decode(SECRET);
        Jws<Claims> claimsJws = jwtParser.setSigningKey(bs).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.getIssuer().equals(account) && claims.getSubject().equals(password);
    }

}
