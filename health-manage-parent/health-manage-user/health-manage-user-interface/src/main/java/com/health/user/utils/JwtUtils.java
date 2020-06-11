package com.health.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param id,username     载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位分钟
     * @return
     * @throws Exception
     */
    public static String generateToken(Long id,String subject, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ID, id)
                .claim(JwtConstans.JWT_KEY_USER_NAME, subject)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param id,subject      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位分钟
     * @return
     * @throws Exception
     */
    public static String generateToken(Long id,String subject, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ID, id)
                .claim(JwtConstans.JWT_KEY_USER_NAME, subject)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())   //默认存储30min
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static Map<String,Object> getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Map<String,Object> map = new HashMap<>();
        map.put("id",ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ID)));
        map.put("username",ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_USER_NAME)));
        return map;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static Map<String,Object> getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Map<String,Object> map = new HashMap<>();
        map.put("id",ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ID)));
        map.put("username",ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_USER_NAME)));
        return map;
    }
}