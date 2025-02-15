package com.sketchuling.category.bo;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryBO {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getCategoryListByUserId(int userId) {

        return categoryRepository.findAllByUserId(userId);
    }
}
