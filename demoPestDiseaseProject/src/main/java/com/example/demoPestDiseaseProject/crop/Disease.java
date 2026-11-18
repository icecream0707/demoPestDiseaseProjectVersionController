package com.example.demoPestDiseaseProject.crop;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 병 (disease 테이블, 약 13행). 작물과 무관한 병 이름 목록이다.
 * 어떤 작물에 어떤 병이 있는지는 {@link CropDisease} 가 연결한다.
 *
 * <p>기준 데이터라 읽기만 한다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 병 코드 (예: late_blight, healthy) */
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    /** 화면 표시용 한글 이름 (예: 역병, 정상) */
    @Column(nullable = false, length = 50)
    private String nameKo;
}
