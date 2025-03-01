package talkPick.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import talkPick.error.ErrorCode;
import talkPick.error.exception.CommonException;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${JASYPT_ADMIN_SECRET_KEY}")
    private String adminKey;

    @Value("${JASYPT_MEMBER_SECRET_KEY}")
    private String memberKey;

    @Value("${JASYPT_ALGORITHM}")
    private String algorithm;

    @Value("${JASYPT_POOL_SIZE}")
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
            throw new CommonException(ErrorCode.JASYPT_KEY_CONFIGURATION_ERROR);
        }
    }
}
