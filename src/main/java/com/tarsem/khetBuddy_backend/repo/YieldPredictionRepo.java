package com.tarsem.khetBuddy_backend.repo;


import com.tarsem.khetBuddy_backend.model.YieldPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YieldPredictionRepo extends JpaRepository<YieldPrediction,Long> {
}
