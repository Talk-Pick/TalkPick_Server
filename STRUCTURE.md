# TalkPick 서버 구조

## 개요
TalkPick 서버는 스몰토크 주제 추천 및 사용자 성향(MBTI 등) 기반 통계 서비스를 제공하는 서비스
헥사고날 아키텍처(Ports & Adapters)를 기반으로 도메인 중심 설계를 따름

## 1. Global (공통 인프라 및 설정)

### `talkPick.global.config`
*   **`AsyncConfig`**: 비동기 작업 처리 설정 (`@EnableAsync`)
*   **`CacheConfig`**: 로컬 캐싱 설정 (Caffeine 등)
*   **`CorsFilter`**: CORS 정책 설정 (허용 출처, 헤더 등)
*   **`JacksonConfig`**: JSON 직렬화/역직렬화 설정
*   **`JasyptConfig`**: 설정 파일 암호화 지원
*   **`JpaAuditingConfig`**: JPA 엔티티 자동 감시 (`BaseTime` 등) 활성화
*   **`QuerydslConfig`**: QueryDSL `JPAQueryFactory` 빈 등록
*   **`SchedulingConfig`**: 스케줄링 작업 활성화 (`@EnableScheduling`)
*   **`SpringDocOpenApiConfig`**: Swagger/OpenAPI 문서 설정
*   **`WebConfig`**: 웹 MVC 설정 (ArgumentResolver 등 등록)

### `talkPick.global.security`
*   **Config**
    *   `SecurityConfig`: Spring Security 체인 설정. CSRF/FormLogin 비활성화, JWT 필터 추가, WhiteList 기반 경로 허용
*   **Filter**
    *   `JwtAuthenticationFilter`: JWT 토큰 파싱 및 유효성 검증, `SecurityContext`에 인증 정보 설정
    *   `ExceptionHandlerFilter`: 필터 체인 내 예외 포착 및 핸들링
*   **JWT**
    *   `JwtProvider`: 토큰 생성, 검증, 정보 추출 (MemberId, Role)
    *   `JwtTokenCommandService`: 액세스/리프레시 토큰 발급 및 재발급
    *   `RefreshTokenRepository`: 리프레시 토큰 저장소
*   **Resolver**
    *   `MemberIdArgumentResolver`: 컨트롤러 파라미터 `@MemberId`를 통해 인증된 사용자 ID 주입

### `talkPick.global.exception`
*   **Handler**: `GlobalExceptionHandler` 및 도메인별 핸들러 (`MemberExceptionHandler`, `TopicExceptionHandler` 등)
*   **Exception**: `TalkPickException` (루트 예외), `ErrorCode` (에러 코드 정의)

### `talkPick.global.rateLimiter`
*   **Adapter**: `RateLimiterManagerAdapter` (Caffeine + Bucket4j 기반 토큰 버킷 알고리즘 구현)
*   **Aspect**: `RateLimiterAspect` (`@RateLimited` 어노테이션이 붙은 메서드 트래픽 제어)
*   **Annotation**: `@RateLimited`

### `talkPick.global.log`
*   **Aspect**: `LoggerAspect` (현재 주석 처리됨, AOP 기반 로깅)

### `talkPick.global.healthCheck`
*   **API**: `DBHealthIndicator`, `UrlHealthIndicator` (시스템 상태 점검)

---

## 2. Domain (핵심 비즈니스 로직)

### **Member (회원)**
*   **Entity**: `Member` (핵심 정보), `MemberLoginHistory` (로그인 기록), `MemberTerm` (약관 동의 내역)
*   **Port (In/Out)**
    *   In: `MemberCommandUseCase`, `MemberQueryUseCase`, `MemberWithdrawalUseCase`
    *   Out: `MemberCommandRepositoryPort`, `MemberQueryRepositoryPort` 등
*   **Service (Application)**
    *   `MemberCommandService`: 회원가입, 프로필 수정(MBTI), 약관 동의, 로그아웃
    *   `MemberQueryService`: 프로필 조회, 좋아요한 토픽 조회, 캘린더 결과 조회
    *   `MemberWithdrawalService`: 회원 탈퇴(Soft Delete) 및 영구 삭제(Hard Delete)
*   **Adapter (Out)**
    *   `MemberJpaRepository`: 기본 CRUD
    *   `MemberLikedTopicsQuerydslRepository`: 좋아요한 토픽 커서 페이징 조회 (복잡한 조인)
    *   `MemberTopicResultQuerydslRepository`: 일자별 토픽 결과 조회

### **Topic (토픽)**
*   **Entity**: `Topic` (주제), `TopicStat` (통계), `Category`, `Keyword`, `TopicLikeHistory`
*   **Port (In/Out)**
    *   In: `TopicCommandUseCase`, `TopicQueryUseCase`
    *   Out: `TopicQueryRepositoryPort`, `TopicLikeHistoryCommandRepositoryPort` 등
*   **Service (Application)**
    *   `TopicCommandService`: 좋아요 기능 (이벤트 발행)
    *   `TopicQueryService`: 카테고리 목록, 토픽 상세 조회
*   **Adapter (Out)**
    *   `TopicQuerydslRepository`: 토픽 검색 및 조회
    *   `TopicStatJpaRepository`: 통계 데이터 관리

### **Random (랜덤 토픽)**
*   **Entity**: `Random` (세션), `RandomTopicHistory` (진행 이력)
*   **Port (In/Out)**
    *   In: `RandomCommandUseCase`, `RandomQueryUseCase`
*   **Service (Application)**
    *   `RandomCommandService`: 세션 시작/종료, 다음 토픽 진행, 평점/한줄평 등록
    *   `RandomQueryService`: 조건별 랜덤 토픽 추천 목록 조회
*   **Adapter (Out)**
    *   `RandomQuerydslRepository`, `RandomTopicHistoryQuerydslRepository`: 동적 쿼리 처리

### **Today (오늘의 토픽)**
*   **Entity**: `TodayTopic` (사용자-토픽 매핑)
*   **Service (Application)**
    *   `TodayTopicQueryService`: 사용자별 오늘의 토픽 조회 (캐싱 적용 `CacheManager`)
*   **Adapter (Out)**
    *   `TodayTopicQuerydslRepository`: 오늘의 토픽 조회 최적화

### **Notice (공지사항)**
*   **Entity**: `Notice`, `NoticeImage`
*   **Service (Application)**
    *   `NoticeQueryService`: 공지사항 목록(커서 페이징) 및 상세 조회
*   **Adapter (Out)**
    *   `NoticeQuerydslRepository`: 페이징 쿼리

### **Inquiry (문의)**
*   **Entity**: `Inquiry`
*   **Service (Application)**
    *   `InquiryCommandService`: 문의 등록
    *   `InquiryQueryService`: 내 문의 내역 조회
*   **Adapter (Out)**
    *   `InquiryQuerydslRepository`: 문의 내역 페이징

### **Term (약관)**
*   **Entity**: `Term`
*   **Adapter (Out)**: `TermJpaRepository`

---

## 3. Batch (배치 및 스케줄러)
*   **`MemberCleanupScheduler`**: 탈퇴 상태인 회원과 연관 데이터를 주기적으로 영구 삭제 (Hard Delete)
*   **`TodayTopicCacheRefreshScheduler`**: 매일 자정 사용자별 새로운 '오늘의 토픽' 생성 및 캐시 갱신
*   **`MasterTokenGenerator`**: 개발/테스트용 마스터 토큰 생성

---

## 4. External (외부 연동)
*   **Kakao**: `KakaoOidcService` (ID Token 검증, 공개키 조회, 사용자 정보 파싱)
*   **Apple**: `AppleOidcService` (애플 로그인 지원)
*   **Google**: `GoogleOidcService` (구글 로그인 지원)
*   **Port**: `KakaoOidcUsecase` 등 인터페이스 정의로 결합도 낮춤