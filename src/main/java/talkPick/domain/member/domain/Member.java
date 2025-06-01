package talkPick.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.auth.Role;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private String email;
    private Role memberRole;
    private String password;
    private String name;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private TalkPickStatus status;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    private String profileImageUrl;


    public Member(MemberEmailReqDTO memberResDto) {
        this.email = memberResDto.getEmail();
        this.name = memberResDto.getName();
        this.password = memberResDto.getPassword();
        this.birth = memberResDto.getBirth();
        this.gender = memberResDto.getGender();
        this.mbti = memberResDto.getMbti();
        this.kakaoId = null;
        this.loginType = LoginType.EMAIL;
        this.status = null;
    }

    public Member(KakaoUserInfo kakaoUserInfo, MBTI mbti) {
        // Initialize maps to prevent NullPointerException
        Map<String, Object> kakaoAccount = (kakaoUserInfo.getKakao_account() != null) 
                ? kakaoUserInfo.getKakao_account() : new HashMap<>();
        Map<String, Object> properties = (kakaoUserInfo.getProperties() != null) 
                ? kakaoUserInfo.getProperties() : new HashMap<>();

        // Handle email safely
        Object emailObj = kakaoAccount.get("email");
        this.email = (emailObj != null) ? String.valueOf(emailObj) : null;

        // Try to get nickname from kakao_account first, then from properties if not found
        Object nicknameObj = kakaoAccount.get("nickname");
        if (nicknameObj == null) {
            nicknameObj = properties.get("nickname");
        }
        this.name = (nicknameObj != null) ? String.valueOf(nicknameObj) : null;
        this.password = null;

        // Handle birth date safely
        String birthStr = String.valueOf(kakaoAccount.get("birthday"));
        if (birthStr != null && !birthStr.equals("null")) {
            try {
                this.birth = LocalDate.parse(birthStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                // If parsing fails, try other common date formats or set to null
                try {
                    // Try MM/dd format (common in some APIs)
                    String[] parts = birthStr.split("/");
                    if (parts.length == 2) {
                        // Assume current year if only month and day are provided
                        String fullDate = LocalDate.now().getYear() + "-" + parts[0] + "-" + parts[1];
                        this.birth = LocalDate.parse(fullDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } else {
                        this.birth = null;
                    }
                } catch (Exception ex) {
                    this.birth = null;
                }
            }
        } else {
            this.birth = null;
        }

        // Handle gender safely
        String genderStr = String.valueOf(kakaoAccount.get("gender"));
        if (genderStr != null && !genderStr.equals("null")) {
            if (genderStr.toUpperCase().equals("MALE")) {
                this.gender = Gender.MALE;
            } else if (genderStr.toUpperCase().equals("FEMALE")) {
                this.gender = Gender.FEMALE;
            } else {
                this.gender = null; // Default to null if gender is not recognized
            }
        } else {
            this.gender = null; // Default to null if gender is not provided
        }
        this.mbti = mbti;
        this.kakaoId = kakaoUserInfo.getId();
        this.loginType = LoginType.KAKAO;
        this.status = null;
    }
}
