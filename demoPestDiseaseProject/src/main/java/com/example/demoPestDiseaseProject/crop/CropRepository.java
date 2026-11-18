package com.example.demoPestDiseaseProject.crop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    /** 요청으로 들어온 작물 코드(TOMATO 등)로 작물 찾기 */
    Optional<Crop> findByCode(String code);
}
