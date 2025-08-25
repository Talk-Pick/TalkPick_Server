package talkPick.global.healthCheck.api;

import org.springframework.boot.actuate.health.Health;

public interface HealthIndicator {
    Health health();
}
