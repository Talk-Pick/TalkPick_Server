package talkPick.domain.member.application.mapper;


import talkPick.domain.member.adapter.in.dto.MemberEmailReqDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.LoginType;

public class MemberMapper {

    public static Member fromDtoToMember(MemberEmailReqDto memberReqDto) {
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
