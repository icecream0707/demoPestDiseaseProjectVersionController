package com.example.demoPestDiseaseProject.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 로그인 시 구글 sub 로 기존 회원 찾기 (없으면 신규 가입) */
    Optional<Member> findByGoogleSub(String googleSub);

    /** 닉네임 중복 확인 */
    boolean existsByNickname(String nickname);
}
