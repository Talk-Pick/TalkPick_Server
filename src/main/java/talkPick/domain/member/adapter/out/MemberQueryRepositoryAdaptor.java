package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.random.dto.MemberInfoDTO;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.member.MemberNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdaptor implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "member:";

    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public Optional<MemberInfoDTO> findMemberInfoById(String memberId) {
        String key = KEY_PREFIX + memberId;
        Object raw = redisTemplate.opsForValue().get(key);
        if (raw instanceof MemberInfoDTO dto) {
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }
}