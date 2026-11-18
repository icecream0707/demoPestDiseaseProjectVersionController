package com.example.demoPestDiseaseProject.diagnosis;

import com.example.demoPestDiseaseProject.crop.CropDisease;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 진단 후보 (diagnosis_candidate 테이블). 진단 1건당 3행 (1~3위).
 * (diagnosis_id, ranking) 조합은 DB 에서 UNIQUE 다.
 *
 * <p>직접 만들지 않고 {@link Diagnosis#addCandidate} 로만 만든다 (그래서 생성자가 package-private).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiagnosisCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 소속 진단 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    /** 순위 1~3 */
    @Column(nullable = false, columnDefinition = "TINYINT")
    private int ranking;

    /** AI 가 추정한 작물×병. 병 이름·설명·치료법은 여기서 꺼낸다 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_disease_id")
    private CropDisease cropDisease;

    /** 신뢰도 0~1 (DECIMAL(5,4), 예: 0.8732) */
    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal confidence;

    DiagnosisCandidate(Diagnosis diagnosis, int ranking, CropDisease cropDisease, BigDecimal confidence) {
        this.diagnosis = diagnosis;
        this.ranking = ranking;
        this.cropDisease = cropDisease;
        this.confidence = confidence;
    }
}
