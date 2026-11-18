package com.example.demoPestDiseaseProject.news;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    /** 작물별 최신 뉴스 (DB 인덱스 (crop_id, published_at) 를 탄다) */
    Page<News> findByCropIdOrderByPublishedAtDesc(Long cropId, Pageable pageable);
}
