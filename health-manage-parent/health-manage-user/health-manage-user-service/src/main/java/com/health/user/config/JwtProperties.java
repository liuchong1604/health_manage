package com.health.user.config;

import com.health.user.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/20 13:28
 */
@Component
@ConfigurationProperties(prefix = "health.jwt")
public class JwtProperties {
    private String secret;

    private String publicKeyPath;

    private String privateKeyPath;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private int expire;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * 在构造方法执行之前执行
     */
    @PostConstruct
    public void init(){
        try{
            File pubKey = new File(publicKeyPath);
            File priKey = new File(privateKeyPath);
            if(!pubKey.exists() || !priKey.exists()) {
                //只要有一个不存在重新生成公钥和私钥
                RsaUtils.generateKey(publicKeyPath, privateKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(publicKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        }catch (Exception e){
            logger.error("初始化公钥和私钥失败!",e);
            throw new RuntimeException();
        }
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
