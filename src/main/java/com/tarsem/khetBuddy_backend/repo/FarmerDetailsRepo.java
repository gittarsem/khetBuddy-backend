package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.entity.FarmerDetails;
import com.tarsem.khetBuddy_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FarmerDetailsRepo extends JpaRepository<FarmerDetails,Long> {
    FarmerDetails findByUserEntity(UserEntity user);
}
