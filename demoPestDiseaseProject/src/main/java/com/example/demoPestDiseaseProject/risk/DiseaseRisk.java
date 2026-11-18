package com.example.demoPestDiseaseProject.risk;

import com.example.demoPestDiseaseProject.crop.CropDisease;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

/**
 * 격자별 병해충 위험 단계 (disease_risk 테이블).
 *
 * <p>Python 이 날씨 데이터를 기준에 따라 가공해서 3시간마다 덮어쓴다 (날씨 테이블은 Java 가 다루지 않음).
 * Java 는 회원의 nx, ny 로 이 테이블만 읽어서 사용자에게 위험도를 보여준다.
 *
 * <p>(nx, ny, crop_disease_id) 조합은 DB 에서 UNIQUE 다. 즉 격자 1곳 × 작물병 1개 = 1행.
 */
@Entity
@Immutable // Python 이 소유한 테이블: Java 는 읽기만 한다
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiseaseRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 기상청 격자 X (member.nx 와 비교) */
    @Column(nullable = false)
    private Integer nx;

    /** 기상청 격자 Y (member.ny 와 비교) */
    @Column(nullable = false)
    private Integer ny;

    /** 판단 대상 작물×병 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_disease_id")
    private CropDisease cropDisease;

    /** 위험 단계: 0 좋음, 1 주의, 2 위험 (기준은 Python disease_risk.py 가 관리) */
    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer riskLevel;

    /** 판단 기준 시각 (수집 회차의 정시) */
    @Column(nullable = false)
    private LocalDateTime calculatedAt;
}
