package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  FarmRepo extends JpaRepository<Farm,Long> {
    List<Farm> findByUser(User user);
}
