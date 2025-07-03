package talkPick.global.rateLimiter.adapter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.global.rateLimiter.port.RateLimiterManager;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static talkPick.global.rateLimiter.constants.RateLimiterConstants.*;

@Slf4j
@Component
public class RateLimiterManagerAdapter implements RateLimiterManager {
    private final Cache<String, Bucket> cache = Caffeine.newBuilder()
            .expireAfterWrite(BUCKET_DURATION_MINUTES, TimeUnit.MINUTES)
            .maximumSize(BUCKET_MAXIMUM_SIZE)
            .build();

    @Override
    public boolean tryConsume(String ipAddress, String uri) {
        String key = ipAddress + BUCKET_KEY + uri;
        return Optional.ofNullable(cache.get(key, k -> newBucket()))
                .filter(bucket -> bucket.tryConsume(BUCKET_TOKEN_NUM))
                .map(bucket -> true)
                .orElseGet(() -> {
                    log.warn("[RateLimiter] 제한 초과 - IP: {}, URI: {}", ipAddress, uri);
                    return false;
                });
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(
                        BUCKET_CAPACITY,
                        Refill.greedy(BUCKET_TOKENS, Duration.ofMinutes(BUCKET_DURATION_MINUTES))
                ))
                .build();
    }
}