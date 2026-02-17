package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User>  findByUsername(String username);

    public boolean existsByUsername(String username);
}
