package com.sketchuling.subcategory.bo;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import com.sketchuling.subcategory.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubcategoryBO {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<SubcategoryEntity> getSubcategoryListByCategoryId(int categoryId) {

        return subcategoryRepository.findAllByCategoryId(categoryId);
    }

    public Map<String, Object> addSubcategory(int id, String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            subcategoryRepository.save(SubcategoryEntity.builder()
                    .categoryId(id)
                    .name(name)
                    .build());
            result.put("code", 200);
        } catch (Exception e) {
            result.put("code", 404);
        }

        return result;
    }
}
