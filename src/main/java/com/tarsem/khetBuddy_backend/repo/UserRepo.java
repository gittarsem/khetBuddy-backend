package com.tarsem.khetBuddy_backend.repo;

import com.tarsem.khetBuddy_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByUsername(String username);

    public boolean existsByUsername(String username);
}
