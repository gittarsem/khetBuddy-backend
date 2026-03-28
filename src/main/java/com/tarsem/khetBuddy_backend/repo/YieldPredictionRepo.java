package com.tarsem.khetBuddy_backend.repo;


import com.tarsem.khetBuddy_backend.entity.YieldPrediction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YieldPredictionRepo extends JpaRepository<YieldPrediction,Long> {
    Page<YieldPrediction> findByFarmId(Long farmId, Pageable pageable);
}
