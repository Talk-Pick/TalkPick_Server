package talkPick.global.rateLimiter.port;

public interface RateLimiterManager {
    boolean tryConsume(String ipAddress, String Uri);
}