package com.example.demoPestDiseaseProject.crop;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 작물 (crop 테이블, 3행: 토마토·감자·옥수수).
 *
 * <p>seed_data.sql 로 넣는 기준 데이터라 앱에서는 읽기만 한다. 그래서 생성자·변경 메서드가 없다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** API 요청·응답에 쓰는 작물 코드: TOMATO | POTATO | CORN */
    @Column(nullable = false, unique = true, length = 10)
    private String code;

    /** 화면 표시용 한글 이름 (예: 토마토) */
    @Column(nullable = false, length = 20)
    private String nameKo;
}
