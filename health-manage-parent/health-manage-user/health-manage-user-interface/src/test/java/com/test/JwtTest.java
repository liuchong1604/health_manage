package com.test;

import com.health.user.pojo.Admin;
import com.health.user.utils.JwtConstans;
import com.health.user.utils.JwtUtils;
import com.health.user.utils.ObjectUtils;
import com.health.user.utils.RsaUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/20 10:13
 */
public class JwtTest {
    private static final String pubKeyPath = "F:\\graduationproject\\health-manage-parent\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "F:\\graduationproject\\health-manage-parent\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 测试生成公钥私钥
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(20L,"jack", privateKey, 10);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU4MjE2NjM5MH0.WIKJs2HfqE198ZSOTtG8MVXEFtcX343hMOdQeaUQN6L_5RHXHHHchOwA2nuJAZYXJS9VaWtw6jXopBtI2H4kumAPJv1Tb4iHkvSKUQXzNh3sT137s_jCpkrB_QcvQcVlN0nVSjDY8rxCIHq2Qx9ROOI0yKtr_4Q0Pz6Xg6YCj1Q";


        // 解析token
        Admin admin = getInfoFromToken(token, publicKey);
        System.out.println("id: " + admin.getId());
        System.out.println("userName: " + admin.getUsername());
    }

        public static Admin getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = JwtUtils.parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Admin admin = new Admin();
        admin.setId(ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ID)));
        admin.setUsername(ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_USER_NAME)));
        return admin;
    }
}
