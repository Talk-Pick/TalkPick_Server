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
        log.warn("\n");
        log.warn("========================================");
        log.warn("마스터 토큰 생성");
        log.warn("========================================");

        JwtResDTO.AccessToken masterToken = jwtGenerator.generateMasterAccessToken(1L, "MEMBER");

        log.warn("========================================");
        log.warn("마스터 토큰 정보");
        log.warn("========================================");
        log.warn("{}", masterToken.accessToken());
        log.warn("========================================\n");
    }
}