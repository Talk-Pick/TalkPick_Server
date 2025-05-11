package talkPick.global.security.jwt.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.global.security.jwt.RefreshToken;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class RefreshTokenGenerator {
    private final RefreshTokenRepository refreshTokenRepository;
    private static final int TOKEN_BYTE_SIZE = 60 * 6 / 8; // 45 Bytes

    public RefreshToken generateRefreshToken(final long userId, final String role) {
        var random = createSecureRandom();

        // SecureRandom을 사용하여 45 바이트의 랜덤 토큰을 생성
        byte[] tokenBytes = new byte[TOKEN_BYTE_SIZE];
        random.nextBytes(tokenBytes);

        // 바이트 배열을 Base64 인코딩하여 문자열로 변환
        var token = Base64.getEncoder().encodeToString(tokenBytes);

        var expireAt = LocalDateTime.now().plusWeeks(4);

        var refreshToken = RefreshToken.of(token, userId, role, expireAt);
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    private SecureRandom createSecureRandom() {
        var buffer = ByteBuffer.allocate(Long.BYTES);

        //현재 시간을 바이트 배열로 변환하여 SecureRandom의 초기값(seed)으로 사용
        buffer.putLong(System.currentTimeMillis());
        return new SecureRandom(buffer.array());
    }

}
