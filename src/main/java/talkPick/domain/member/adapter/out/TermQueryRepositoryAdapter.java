package talkPick.domain.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.term.adapter.out.repository.TermJpaRepository;
import talkPick.domain.term.domain.Term;
import talkPick.domain.term.port.out.TermQueryRepositoryPort;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TermQueryRepositoryAdapter implements TermQueryRepositoryPort {
    private final TermJpaRepository repository;

    @Override
    public Optional<Term> findById(Long termId) {
        return repository.findById(termId);
    }

    @Override
    public List<Term> findByIsRequiredTrue() {
        return repository.findByIsRequiredTrue();
    }
}


