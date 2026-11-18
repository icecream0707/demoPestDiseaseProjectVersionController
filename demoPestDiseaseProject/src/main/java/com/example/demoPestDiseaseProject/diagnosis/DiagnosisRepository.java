package com.example.demoPestDiseaseProject.diagnosis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/** 두 메서드 모두 DB 인덱스 (member_id, created_at) 를 탄다 */
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    /** 내 진단 이력 목록 (최신순, 페이지 단위) */
    Page<Diagnosis> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    /** 오늘 진단 횟수 세기: from 에 오늘 0시를 넘긴다 */
    long countByMemberIdAndCreatedAtGreaterThanEqual(Long memberId, LocalDateTime from);
}
