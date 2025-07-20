package talkPick.global.healthCheck.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import talkPick.global.healthCheck.exception.HealthCheckException;
import static talkPick.global.exception.ErrorCode.DB_HEALTH_CHECK_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBHealthIndicator implements HealthIndicator {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Health.up().build();
        } catch (Exception e) {
            log.error("[Health Check Error]", e);
            throw new HealthCheckException(DB_HEALTH_CHECK_FAILED);
        }
    }
}