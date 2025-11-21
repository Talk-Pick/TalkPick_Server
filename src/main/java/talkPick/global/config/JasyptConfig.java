package talkPick.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${jasypt.admin.secret-key}")
    private String adminKey;

    @Value("${jasypt.member.secret-key}")
    private String memberKey;

    @Value("${jasypt.algorithm}")
    private String algorithm;

    @Value("${jasypt.pool-size}")
    private Integer poolSize;

    @Bean("adminEncryptor")
    public StringEncryptor adminEncryptor() {
        return createEncryptor(adminKey);
    }

    @Bean("memberEncryptor")
    public StringEncryptor memberEncryptor() {
        return createEncryptor(memberKey);
    }

    private StringEncryptor createEncryptor(String key) {
        validateKey(key);
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPassword(key);
        encryptor.setAlgorithm(algorithm);
        encryptor.setPoolSize(poolSize);
        return encryptor;
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new TalkPickException(ErrorCode.JASYPT_KEY_CONFIGURATION_ERROR);
        }
    }
}