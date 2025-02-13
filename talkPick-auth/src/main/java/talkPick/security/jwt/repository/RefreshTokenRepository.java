package talkPick.security.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import tictoc.config.security.jwt.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByUserId(final Long userId);
    RefreshToken findByToken(String token);
}
