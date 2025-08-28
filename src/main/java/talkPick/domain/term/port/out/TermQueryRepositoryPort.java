package talkPick.domain.term.port.out;

import talkPick.domain.term.domain.Term;

import java.util.List;
import java.util.Optional;

public interface TermQueryRepositoryPort {
    Optional<Term> findById(Long termId);
    List<Term> findByIsRequiredTrue();
}


