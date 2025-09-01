package talkPick.global.security.jwt.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import talkPick.global.security.jwt.port.in.RedisCommandUseCase;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisCommandService implements RedisCommandUseCase {
    private final StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public void addTokenToBlacklist(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", expirationMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        // 토큰에 Bearer가 없으면 자동 추가
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void removeTokenFromBlacklist(String token) {
        redisTemplate.delete(BLACKLIST_PREFIX + token);
    }

}
