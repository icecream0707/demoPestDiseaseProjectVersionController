package com.example.demoPestDiseaseProject.crop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropDiseaseRepository extends JpaRepository<CropDisease, Long> {

    /** FastAPI 진단 결과 라벨로 작물×병 행 찾기 */
    Optional<CropDisease> findByLabel(String label);
}
