package talkPick.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${admin.jasypt.encryptor.password}")
    private String adminKey;

    @Value("${member.jasypt.encryptor.password}")
    private String memberKey;

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;

    @Value("${jasypt.encryptor.pool-size}")
    private Integer poolSize;

    @Bean("adminEncryptor")
    public StringEncryptor adminEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPassword(adminKey); // Admin 전용 키
        encryptor.setAlgorithm(algorithm);
        encryptor.setPoolSize(poolSize);
        return encryptor;
    }

    @Bean("memberEncryptor")
    public StringEncryptor memberEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPassword(memberKey); // Member 전용 키
        encryptor.setAlgorithm(algorithm);
        encryptor.setPoolSize(poolSize);
        return encryptor;
    }
}