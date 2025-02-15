package com.sketchuling.subcategory.bo;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import com.sketchuling.subcategory.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubcategoryBO {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<SubcategoryEntity> getSubcategoryListByCategoryId(int categoryId) {

        return subcategoryRepository.findAllByCategoryId(categoryId);
    }
}
