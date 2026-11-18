package com.example.demoPestDiseaseProject.crop;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 작물 × 병 조합 (crop_disease 테이블, 17행).
 * AI 모델(PlantVillage)의 라벨 1개가 이 테이블 1행이다.
 *
 * <p>진단 결과(diagnosis_candidate)와 위험도(disease_risk)가 모두 이 행을 가리킨다.
 * (crop_id, disease_id) 조합은 DB 에서 UNIQUE 다.
 *
 * <p>기준 데이터라 읽기만 한다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 작물의 병인지. LAZY: 실제로 crop 값을 쓸 때만 조회한다 (N+1 은 조회 쿼리에서 fetch join 으로 해결) */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    /** FastAPI 가 돌려주는 라벨 그대로 (예: Corn_(maize)___Common_rust_). AI 결과를 이 행과 맞출 때만 쓴다 */
    @Column(nullable = false, unique = true, length = 100)
    private String label;

    /** 병 설명 (정의) */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /** 치료법 */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String treatment;
}
