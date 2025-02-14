package com.sketchuling.subcategory.repository;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {
    public List<SubcategoryEntity> findAllByCategoryId(int categoryId);
}
