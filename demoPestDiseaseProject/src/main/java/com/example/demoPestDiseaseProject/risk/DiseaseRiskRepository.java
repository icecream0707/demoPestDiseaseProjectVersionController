package com.example.demoPestDiseaseProject.risk;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiseaseRiskRepository extends JpaRepository<DiseaseRisk, Long> {

    /**
     * 내 격자의 위험도 전체 조회.
     * 응답에 작물·병 이름이 필요하므로 한 쿼리로 같이 가져온다 (없으면 행마다 추가 쿼리가 나가는 N+1 발생).
     */
    @EntityGraph(attributePaths = {"cropDisease", "cropDisease.crop", "cropDisease.disease"})
    List<DiseaseRisk> findByNxAndNy(Integer nx, Integer ny);
}
