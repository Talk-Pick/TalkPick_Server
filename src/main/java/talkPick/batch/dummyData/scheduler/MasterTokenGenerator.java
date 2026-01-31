package talkPick.batch.dummyData.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import talkPick.domain.auth.port.out.TokenGeneratorPort;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class MasterTokenGenerator {
    private final TokenGeneratorPort tokenGeneratorPort;

    @EventListener(ApplicationReadyEvent.class)
    public void generateMasterToken() {
        log.info("[MasterToken Generated] AccessToken: {}", tokenGeneratorPort.generateMasterAccessToken(1L, "MEMBER"));
    }
}