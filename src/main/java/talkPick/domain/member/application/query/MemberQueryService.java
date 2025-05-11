package talkPick.domain.member.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.port.in.MemberQueryUseCase;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberQueryRepositoryPort memberQueryRepository;
}
