# 💬 TalkPick Server

## 🖥️ 프로젝트 소개
대화 주제 추천 서비스입니다.

<br>

## 🟢 ERD

<br>

## 🛠️ 아키텍처 구조

<br>

## 🕰️ 개발 기간
* 25.01.13 - 진행 중
  
<br>
  
## ⚙️ 개발 환경
- `Java 21`
- **IDE** : IntelliJ IDEA
- **Framework** : Springboot(3.3.7)
- **Database** : PostgreSQL
- **ORM** : Hibernate (Spring Data JPA 사용)

<br>
  
## 🧑‍🤝‍🧑 멤버 구성
<p>
    <a href="https://github.com/M-ung">
      <img src="https://avatars.githubusercontent.com/u/126846468?v=4" width="100">
    </a>
</p>
  
<br>
  
## 📝 규칙
  
- **커밋 컨벤션**
    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정
  
- **Branch 규칙**
    - 각자의 깃 타입과 이슈번호를 딴 branch 명을 사용한다.
    - 예시
        - git checkout -b 타입/#이슈번호
        - git checkout -b feature/#5
  
- **Commit message 규칙**
    - "타입(앞글자를 대문자로): 커밋 메세지 - #이슈번호" 형식으로 작성한다.
    - 예시
        - Feat: 커밋 내용 - #이슈번호
        - Feat: 로그인 구현 - #5
  
- **DTO 규칙**
    - 엔티티명 + Response/Request + DTO
    - 예시
        - UserResponseDTO
        - PostRequestDTO
