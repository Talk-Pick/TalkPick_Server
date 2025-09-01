package talkPick.global.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("jwt")
@Component
public class JwtProperties {
    private String secret;
    private long accessTokenExpireTime = 30; // 기본값: 30분
    private long refreshTokenExpireTime = 40320; // 기본값: 4주
}