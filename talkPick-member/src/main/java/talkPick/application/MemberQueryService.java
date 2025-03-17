package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.port.in.MemberQueryUseCase;
import talkPick.port.out.MemberQueryRepositoryPort;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberQueryRepositoryPort memberQueryRepository;
}
