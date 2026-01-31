package talkPick.core.healthCheck.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import talkPick.core.common.exception.handler.HealthCheckExceptionHandler;
import static talkPick.core.common.exception.ErrorCode.URL_HEALTH_CHECK_FAILED;

@Slf4j
@Component
public class UrlHealthIndicator implements HealthIndicator {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${healthcheck.url}")
    private String url;

    @Override
    public Health health() {
        try {
            restTemplate.getForEntity(url, String.class);
            return Health.up().withDetail("url", url).build();
        } catch (Exception e) {
            log.error("[Health Check Error]", e);
            throw new HealthCheckExceptionHandler(URL_HEALTH_CHECK_FAILED);
        }
    }
}