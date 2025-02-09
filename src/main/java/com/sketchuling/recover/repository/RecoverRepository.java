package com.sketchuling.recover.repository;

import com.sketchuling.user.entity.RecoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecoverRepository extends JpaRepository<RecoverEntity, Integer> {
    public RecoverEntity findByLoginIdAndEmailAndCode(String loginId, String email, String code);
}
