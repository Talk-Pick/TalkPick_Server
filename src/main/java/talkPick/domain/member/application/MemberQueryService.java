package talkPick.domain.member.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.in.MemberQueryUseCase;
import talkPick.domain.member.exception.MemberNotFoundException.MemberLikedTopicsNotFoundException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberJpaRepository memberJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MemberEmailResDTO> getEmailMembers() {
        List<Member> all = memberJpaRepository.findAll();
        List<MemberEmailResDTO> memberResDtoList = all.stream()
                .filter(m -> m.getKakaoId() == null)
                .map(m -> new MemberEmailResDTO(m))
                .collect(Collectors.toList());
        return memberResDtoList;
    }

    @Override
    public List<MemberKakaoResDTO> getkakaoMembers() {
        List<Member> all = memberJpaRepository.findAll();
        List<MemberKakaoResDTO> memberResDtoList = all.stream()
                .filter(m -> m.getKakaoId() != null)
                .map(m -> new MemberKakaoResDTO(m))
                .collect(Collectors.toList());
        return memberResDtoList;
    }

    @Override
    public MemberDetailResDto getMemberInfo(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        return MemberDetailResDto.fromEntity(member);
    }

    @Override
    public Optional<Member> findByKakaoId(String kakaoId) {
        if (kakaoId == null || kakaoId.trim().isEmpty()) {
            throw new IllegalArgumentException("카카오 ID는 비어있을 수 없습니다.");
        }

        return memberJpaRepository.findByKakaoId((kakaoId));
    }

    @Override
    public Optional<Member> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("회원 ID는 null일 수 없습니다.");
        }

        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 비어있을 수 없습니다.");
        }

        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Page<MemberLikedTopicsResDto> getMemberLikedTopics(Long memberId, Pageable pageable) {
        String countJpql = "SELECT COUNT(DISTINCT t.id) " +
                "FROM MemberTopicHistory mth " +
                "JOIN mth.topic t " +
                "JOIN Category c ON t.categoryId = c.id " +
                "JOIN TopicKeyword k ON t.id = k.topicId " +
                "JOIN TopicStat ts ON t.id = ts.topicId " +
                "WHERE mth.member.id = :memberId " +
                "AND mth.checkLiked = true";

        Long totalCount = entityManager.createQuery(countJpql, Long.class)
                .setParameter("memberId", memberId)
                .getSingleResult();

        String jpql = "SELECT DISTINCT new talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto(" +
                "t.title, ts.averageTalkTime, ts.selectCount, k.keyword, c) " +
                "FROM MemberTopicHistory mth " +
                "JOIN mth.topic t " +
                "JOIN Category c ON t.categoryId = c.id " +
                "JOIN TopicKeyword k ON t.id = k.topicId " +
                "JOIN TopicStat ts ON t.id = ts.topicId " +
                "WHERE mth.member.id = :memberId " +
                "AND mth.checkLiked = true";

        List<MemberLikedTopicsResDto> results = entityManager.createQuery(jpql, MemberLikedTopicsResDto.class)
                .setParameter("memberId", memberId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        if (results.isEmpty()) {
            throw new MemberLikedTopicsNotFoundException("해당 회원이 좋아요한 토픽을 찾을 수 없습니다. 회원 ID: " + memberId);
        }

        // 페이지 객체 생성 및 반환
        return new PageImpl<>(results, pageable, totalCount);
    }
}
