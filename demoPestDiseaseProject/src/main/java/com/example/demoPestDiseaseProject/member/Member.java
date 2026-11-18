package com.example.demoPestDiseaseProject.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 회원 (member 테이블).
 *
 * <p>구글 로그인으로 처음 들어올 때 googleSub, email 만으로 만들어지고,
 * 닉네임과 주소는 이후 화면에서 따로 채운다.
 *
 * <p>setter 는 두지 않는다. 값은 아래 change* 메서드로만 바꿔서 "어디서 무엇을 바꾸는지"가 드러나게 한다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 가 쓰는 기본 생성자. 외부에서 빈 객체를 못 만들게 막는다
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT
    private Long id;

    /** 구글 계정 고유 ID (OAuth2 의 sub). 로그인한 사용자를 찾는 기준 */
    @Column(nullable = false, unique = true)
    private String googleSub;

    /** 구글 이메일. 관리자 이메일과 비교해 ADMIN 을 자동 지정할 때 쓴다 */
    @Column(nullable = false, unique = true)
    private String email;

    /** 닉네임. 첫 로그인 직후에는 null 이다 */
    @Column(length = 50, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING) // 숫자(0,1)가 아니라 "USER" 문자열로 저장
    @Column(nullable = false, length = 10)
    private Role role;

    /** 도로명 주소 (카카오 우편번호 서비스의 data.roadAddress) */
    @Column(length = 200)
    private String roadAddr;

    /** 법정동 코드 10자리 (카카오 data.bcode) */
    @Column(columnDefinition = "CHAR(10)")
    private String admCd;

    /** 기상청 격자 X. disease_risk 에서 내 지역 위험도를 찾을 때 쓴다 */
    private Integer nx;

    /** 기상청 격자 Y */
    private Integer ny;

    @CreationTimestamp // INSERT 시 현재 시각 자동 입력
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // UPDATE 시 현재 시각 자동 갱신
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** 첫 로그인 시 회원 생성. ADMIN 여부는 서비스에서 이메일을 보고 정해서 넘긴다 */
    public Member(String googleSub, String email, Role role) {
        this.googleSub = googleSub;
        this.email = email;
        this.role = role;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /** 주소 변경. 주소와 함께 그 지역의 격자(nx, ny)도 같이 바꾼다 */
    public void changeAddress(String roadAddr, String admCd, Integer nx, Integer ny) {
        this.roadAddr = roadAddr;
        this.admCd = admCd;
        this.nx = nx;
        this.ny = ny;
    }
}
