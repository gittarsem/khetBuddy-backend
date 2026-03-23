package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  FarmRepo extends JpaRepository<Farm,Long> {
    List<Farm> findByUserEntity(UserEntity userEntity);
}
