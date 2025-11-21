package talkPick.batch.dummyData.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterTokenGenerator {
    private final JwtGenerator jwtGenerator;

    @EventListener(ApplicationReadyEvent.class)
    public void generateMasterToken() {
        log.debug("\n");
        log.debug("마스터 토큰 생성");
        log.debug("========================================");

        JwtResDTO.AccessToken masterToken = jwtGenerator.generateMasterAccessToken(1L, "MEMBER");

        log.debug("{}", masterToken.accessToken());
        log.debug("========================================\n");
    }
}