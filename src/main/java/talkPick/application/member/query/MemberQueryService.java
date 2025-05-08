package talkPick.application.member.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.port.in.member.MemberQueryUseCase;
import talkPick.port.out.member.MemberQueryRepositoryPort;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberQueryRepositoryPort memberQueryRepository;
}
