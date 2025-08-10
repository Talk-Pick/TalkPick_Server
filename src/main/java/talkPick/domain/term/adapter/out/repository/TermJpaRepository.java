package talkPick.domain.term.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.term.domain.Term;

import java.util.List;

public interface TermJpaRepository extends JpaRepository<Term, Long> {
    List<Term> findByIsRequiredTrue();
}
