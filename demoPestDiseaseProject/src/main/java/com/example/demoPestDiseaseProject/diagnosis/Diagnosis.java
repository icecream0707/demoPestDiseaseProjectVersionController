package com.example.demoPestDiseaseProject.diagnosis;

import com.example.demoPestDiseaseProject.common.exception.BusinessException;
import com.example.demoPestDiseaseProject.common.exception.ErrorCode;
import com.example.demoPestDiseaseProject.crop.Crop;
import com.example.demoPestDiseaseProject.crop.CropDisease;
import com.example.demoPestDiseaseProject.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 진단 1건 (diagnosis 테이블).
 *
 * <p>사용자가 사진을 올리면 1건이 생기고, AI 결과 상위 3개가 {@link DiagnosisCandidate} 로 함께 저장된다.
 * 삭제는 없고, 저장 후 바뀌는 값은 feedback 하나뿐이다 (그것도 1회만).
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 진단한 회원 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    /** 사용자가 고른 작물 (AI 가 추정한 작물이 아님) */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id")
    private Crop crop;

    /** 원본 이미지 파일명 ({UUID}.jpg) */
    @Column(nullable = false, length = 100)
    private String imageKey;

    /** 썸네일 파일명 ({UUID}_thumb.jpg) */
    @Column(nullable = false, length = 100)
    private String thumbnailKey;

    /** 1위 신뢰도가 0.5 미만이면 true. 화면에서 "확실하지 않음" 안내에 쓴다 */
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean uncertain;

    /** 사용자 피드백. 아직 안 남겼으면 null */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Feedback feedback;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 후보 3개 (1~3위).
     * cascade ALL: 진단을 저장하면 후보도 같이 저장된다. 그래서 후보 전용 Repository 가 없다.
     */
    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL)
    @OrderBy("ranking ASC")
    private List<DiagnosisCandidate> candidates = new ArrayList<>();

    public Diagnosis(Member member, Crop crop, String imageKey, String thumbnailKey, boolean uncertain) {
        this.member = member;
        this.crop = crop;
        this.imageKey = imageKey;
        this.thumbnailKey = thumbnailKey;
        this.uncertain = uncertain;
    }

    /** 후보 추가. 진단을 save 하기 전에 1~3위를 순서대로 넣는다 */
    public void addCandidate(int ranking, CropDisease cropDisease, BigDecimal confidence) {
        candidates.add(new DiagnosisCandidate(this, ranking, cropDisease, confidence));
    }

    /** 피드백 남기기. 이미 남겼으면 409 에러 */
    public void giveFeedback(Feedback feedback) {
        if (this.feedback != null) {
            throw new BusinessException(ErrorCode.FEEDBACK_ALREADY_GIVEN);
        }
        this.feedback = feedback;
    }
}
