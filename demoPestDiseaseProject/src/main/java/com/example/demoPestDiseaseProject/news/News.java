package com.example.demoPestDiseaseProject.news;

import com.example.demoPestDiseaseProject.crop.Crop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

/**
 * 작물 관련 뉴스 (news 테이블).
 *
 * <p>Python 이 매일 06시에 수집해서 넣는다 (같은 url 은 건너뜀). Java 는 읽기만 한다.
 */
@Entity
@Immutable // Python 이 소유한 테이블: Java 는 읽기만 한다
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 작물에 대한 뉴스인지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @Column(nullable = false, length = 300)
    private String title;

    /** 기사 원문 주소. 중복 수집 방지를 위해 DB 에서 UNIQUE */
    @Column(nullable = false, unique = true, length = 500)
    private String url;

    /** 기사 발행 시각 */
    @Column(nullable = false)
    private LocalDateTime publishedAt;

    /** 수집해서 DB 에 넣은 시각 (DB 가 기본값으로 채움) */
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
