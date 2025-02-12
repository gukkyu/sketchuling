package com.sketchuling.category.repository;

import com.sketchuling.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    public List<CategoryEntity> findAllByUserId(int userId);
}
