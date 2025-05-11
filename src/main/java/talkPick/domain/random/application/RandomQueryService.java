package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.port.in.RandomQueryUseCase;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomQueryService implements RandomQueryUseCase {
}
