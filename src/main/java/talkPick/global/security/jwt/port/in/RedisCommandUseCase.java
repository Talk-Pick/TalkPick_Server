package talkPick.global.security.jwt.port.in;

public interface RedisCommandUseCase {
    void addTokenToBlacklist(String token, long expirationMillis);
    boolean isTokenBlacklisted(String token);
    void removeTokenFromBlacklist(String token);
}
