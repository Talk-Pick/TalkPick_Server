package talkPick.member;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.adapter.in.MemberCommandController;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.adapter.in.dto.MemberMbtiUpdateRequestDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.application.MemberCommandService;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberCommandControllerTest {

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private MemberCommandService memberCommandService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberCommandController memberCommandController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberCommandController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("이메일 기반 회원가입")
    void testJoinEmailMemberSuccess() throws Exception {
        MemberEmailReqDTO reqDTO = new MemberEmailReqDTO();
        reqDTO.setEmail("test@example.com");
        reqDTO.setPassword("password123");
        reqDTO.setName("테스터");

        String encodedPassword = "encodedPassword";
        //passwordEncoder 동작 조정
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

        Member savedMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .name("테스터")
                .password(encodedPassword)
                .build();

        when(memberQueryService.findByEmail("test@example.com"))
                .thenReturn(Optional.empty()) // 첫 번째 호출 -> 회원 존재 x
                .thenReturn(Optional.of(savedMember)); // 두 번째 호출 -> 회원 존재

        JwtResDTO.Login jwtToken = new JwtResDTO.Login( // jwt 토큰 설정
                1L,
                "ROLE_MEMBER",
                "access-token-value",
                "refresh-token-value",
                System.currentTimeMillis() + 3600000L,
                System.currentTimeMillis() + 604800000L
        );
        // 어떤 Long값이나 String을 인자로 받더라도 createJWT 동작 일관되게 동작하도록 설정
        // 외부 HTTP 의존성 제거
        when(jwtProvider.createJwt(anyLong(), anyString())).thenReturn(jwtToken);
        doNothing().when(jwtProvider).addTokenCookies(any(HttpServletResponse.class), any(JwtResDTO.Login.class));

        MockHttpServletResponse response = new MockHttpServletResponse();
        ResponseEntity<?> responseEntity = memberCommandController.joinEmailMember(reqDTO, response);

        verify(memberCommandService).setEmailMember(argThat(dto ->
                dto.getEmail().equals("test@example.com") &&
                        dto.getPassword().equals(encodedPassword) &&
                        dto.getName().equals("테스터")
        ));

        // 중복 이메일 검사 후 회원 가입 완료 후 2번 검증
        verify(memberQueryService, times(2)).findByEmail("test@example.com");

        // jwtProvider 토큰 생성 확인
        verify(jwtProvider).createJwt(eq(1L), eq(String.valueOf(Role.MEMBER)));
        verify(jwtProvider).addTokenCookies(same(response), any(JwtResDTO.Login.class));

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isInstanceOf(MemberEmailResDTO.class);
        MemberEmailResDTO responseDTO = (MemberEmailResDTO) responseEntity.getBody();
        assertThat(responseDTO.getEmail()).isEqualTo("test@example.com");

        if (response.getCookies() != null && response.getCookies().length > 0) {
            Cookie[] cookies = response.getCookies();

            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "access_token".equals(cookie.getName()))
                    .findFirst();

            Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "refresh_token".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent() && refreshTokenCookie.isPresent()) {
                assertThat(accessTokenCookie.get().getValue()).isEqualTo("access-token-value");
                assertThat(refreshTokenCookie.get().getValue()).isEqualTo("refresh-token-value");
            }
        }
    }

    @Test
    @DisplayName("회원 MBTI 수정 성공 테스트")
    void testUpdateMemberMbtiSuccess() throws Exception {
        // Given
        Long memberId = 1L;
        MBTI originalMbti = MBTI.INFP;
        MBTI newMbti = MBTI.ENFJ;

        Member member = Member.builder()
                .id(memberId)
                .email("test@example.com")
                .name("테스터")
                .mbti(originalMbti)
                .build();

        Member updatedMember = Member.builder()
                .id(memberId)
                .email("test@example.com")
                .name("테스터")
                .mbti(newMbti)
                .build();

        MemberMbtiUpdateRequestDto requestDto = new MemberMbtiUpdateRequestDto(newMbti);

        // MemberDetailResDto 객체를 올바르게 생성 (Builder 패턴 사용)
        MemberDetailResDto memberDetailResDto = MemberDetailResDto.builder()
                .email("test@example.com")
                .birthDate("2000.01.01") // 예시 생년월일
                .mbti(newMbti)
                .nickname("테스터")
                .imageUrl("https://example.com/profile.jpg") // 예시 이미지 URL
                .build();

        // Authentication 모킹
        when(memberQueryService.findById(memberId)).thenReturn(Optional.of(member));
        when(memberCommandService.updateMemberMbti(eq(memberId), eq(newMbti))).thenReturn(updatedMember);
        when(memberQueryService.getMemberInfo(memberId)).thenReturn(memberDetailResDto);

        // 보안 컨텍스트 모킹
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(String.valueOf(memberId));

        System.out.println("수정 전 MBTI: " + originalMbti);

        // When & Then
        mockMvc.perform(put("/api/v1/members/mbti")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.mbti").value(newMbti.toString()))
                .andExpect(jsonPath("$.nickname").value("테스터")).andDo(result -> {
                    // 응답 내용 가져오기
                    String responseContent = result.getResponse().getContentAsString();
                    // JSON 응답에서 MBTI 정보 추출
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(responseContent);
                    String updatedMbtiValue = rootNode.get("mbti").asText();

                    // 수정 후 MBTI 출력
                    System.out.println("수정 후 MBTI: " + updatedMbtiValue);
                });


        // 서비스 메소드 호출 검증
        verify(memberQueryService, times(1)).findById(memberId);
        verify(memberCommandService, times(1)).updateMemberMbti(memberId, newMbti);
        verify(memberQueryService, times(1)).getMemberInfo(memberId);
    }




}
