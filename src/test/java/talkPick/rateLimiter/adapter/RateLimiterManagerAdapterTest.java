package talkPick.rateLimiter.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.global.rateLimiter.adapter.RateLimiterManagerAdapter;

import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

class RateLimiterManagerAdapterTest {
    private RateLimiterManagerAdapter rateLimiter;

    @BeforeEach
    void setUp() {
        rateLimiter = new RateLimiterManagerAdapter();
    }

    @Test
    @DisplayName("✅ Rate Limiter 요청 테스트")
    void 요청_테스트() {
        String ip = "192.168.0.1";
        String uri = "/api/test";

        IntStream.rangeClosed(1, 10).forEach(i -> {
            boolean allowed = rateLimiter.tryConsume(ip, uri);
            assertThat(allowed).as("Request " + i + " should be allowed").isTrue();
        });

        boolean eleventh = rateLimiter.tryConsume(ip, uri);
        assertThat(eleventh).as("11th request should be rate limited").isFalse();
    }

    @Test
    @DisplayName("✅ Rate Limiter 버킷 테스트")
    void 버킷_테스트() {
        String uri = "/api/test";

        IntStream.rangeClosed(1, 10).forEach(i -> {
            assertThat(rateLimiter.tryConsume("192.168.0.1", uri)).isTrue();
        });
        assertThat(rateLimiter.tryConsume("192.168.0.1", uri)).isFalse();
        assertThat(rateLimiter.tryConsume("192.168.0.2", uri)).isTrue();
        assertThat(rateLimiter.tryConsume("192.168.0.1", "/api/another")).isTrue();
    }
}