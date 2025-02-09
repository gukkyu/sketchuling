package com.sketchuling.user.repository;

import com.sketchuling.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public Optional<UserEntity> findByLoginId(String loginId);
    public UserEntity findByLoginIdAndPassword(String loginId, String password);
    public UserEntity findByNameAndEmail(String name, String email);
    public UserEntity findByLoginIdAndEmail(String loginId, String email);
}
