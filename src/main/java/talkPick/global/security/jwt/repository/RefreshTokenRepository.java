package talkPick.global.security.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import talkPick.global.security.jwt.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByMemberId(final Long memberId);
    RefreshToken findByToken(String token);
}
