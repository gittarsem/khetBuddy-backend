package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.model.FarmerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerDetailsRepo extends JpaRepository<FarmerDetails,Long> {
}
