package com.lab3.util;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    //生成签名密钥
    private static final String JWT_SECRET="8677df7fc3a34e26a61c034d5ec8245d";
    //设置过期时间
    private final static int expiresSecond = 172800000;
    public static SecretKey generalKey(){
        byte[] encodedKey= Base64.getDecoder().decode(JWT_SECRET);
        return new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
    }

    public static String createJWT(final String identity,final int id){
        Date now=new Date();

        //添加构成JWT的参数
        JwtBuilder jwtBuilder= Jwts.builder()
                .setId("token")
                .setSubject("token")
                .claim("identity",identity)
                .claim("id",id)
                .claim("time",now.getTime())
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,generalKey());
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long nowMillis = System.currentTimeMillis();
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp).setNotBefore(now);
        }

        return jwtBuilder.compact();
    }

    public static Claims parse(String token){
        if (token==null || "".equals(token)){
            return null;
        }
        Claims claims=null;
        try {
            claims=Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e){
            System.out.println("解析失败");
        }
        return claims;
    }
}
