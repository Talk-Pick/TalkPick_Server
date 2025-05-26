package talkPick.domain.admin.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.admin.adapter.in.dto.AdminReqDTO;
import talkPick.domain.admin.port.in.AdminTopicCommandUseCase;
import talkPick.domain.admin.port.in.AuthCommandUseCase;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.admin.adapter.out.dto.AdminResDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.security.util.CustomUserDetails;
import talkPick.global.security.jwt.dto.JwtResDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminCommandController {
    private final AuthCommandUseCase authCommandUseCase;
    private final AdminTopicCommandUseCase adminTopicCommandUseCase;

    @PostMapping("/signup")
    @Operation(summary = "관리자 회원가입", description = "POST")
    public AdminResDTO.Signup adminSignup(@RequestBody @Valid AdminReqDTO.Signup signup) {
        return authCommandUseCase.signup(signup);
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인", description = "POST")
    public JwtResDTO.Login adminLogin(@RequestBody @Valid AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO) {
        return authCommandUseCase.login(login, jwtResDTO);
    }

    @PostMapping("/topic-card")
    @Operation(summary = "topic 카드 추가", description = "POST")
    public TopicResDTO.Topic createTopicCard(@AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid TopicReqDTO.CreateTopic topicRequest) {
        return adminTopicCommandUseCase.createTopicByAdmin(user.getId(), topicRequest);
    }

    @PutMapping("/topic-card/{topicId}")
    @Operation(summary = "topic 카드 수정", description = "PUT")
    public TopicResDTO.Topic updateTopicCard(@AuthenticationPrincipal CustomUserDetails user,  @PathVariable Long topicId, @RequestBody @Valid TopicReqDTO.CreateTopic topicRequest) {
        return adminTopicCommandUseCase.updateTopicByAdmin(topicId, topicRequest);
    }

    @DeleteMapping("/topic-card/{topicId}")
    @Operation(summary = "topic 카드 삭제", description = "DELETE")
    public void deleteTopicCard(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long topicId) {
        adminTopicCommandUseCase.deleteTopicByAdmin(topicId);
    }
}
