package talkPick.domain.member.application.mapper;


import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.profile.Profile;
import talkPick.domain.member.domain.type.LoginType;

public class MemberMapper {

    public static Profile fromDtoToProfile(MemberEmailReqDTO memberReqDto, Member saveMember) {
        Profile saveProfile = Profile.builder()
                .member(saveMember)
                .mbti(memberReqDto.getMbti())
                .build();
        return saveProfile;
    }

    public static Member fromDtoToMember(MemberEmailReqDTO memberReqDto) {
        Member saveMember = Member.builder()
                .email(memberReqDto.getEmail())
                .name(memberReqDto.getName())
                .password(memberReqDto.getPassword())
                .birth(memberReqDto.getBirth())
                .gender(memberReqDto.getGender())
                .mbti(memberReqDto.getMbti())
                .kakaoId(null)
                .loginType(LoginType.EMAIL)
                .status(null)
                .build();
        return saveMember;
    }
}
