package talkPick.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberJpaRepository memberJpaRepository;

    public Optional<Member> findByKakaoId(String kakaoId) {
        if (kakaoId == null || kakaoId.trim().isEmpty()) {
            throw new IllegalArgumentException("카카오 ID는 비어있을 수 없습니다.");
        }

        return memberJpaRepository.findByKakaoId((kakaoId));
    }

}
