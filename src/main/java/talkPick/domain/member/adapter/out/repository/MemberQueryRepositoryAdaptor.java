package talkPick.domain.member.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.member.MemberNotFoundException;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MemberQueryRepositoryAdaptor implements MemberQueryRepositoryPort {
    private final MemberJpaRepository memberJpaRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "member:";

    @Override
    public MemberDataDTO findMemberDataById(final Long memberId) {
        var key = KEY_PREFIX + memberId;
        var cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof MemberDataDTO dto) {
            return dto;
        }

        var member = findMemberById(memberId);
        var dto = MemberDataDTO.from(member);
        redisTemplate.opsForValue().set(key, dto, Duration.ofHours(5));
        return dto;
    }

    @Override
    public Member findMemberById(final Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}