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

    public List<Integer> getCategoryIdListByUserId(int userId) {

        List<CategoryEntity> categoryEntityList = categoryRepository.findAllByUserId(userId);
        List<Integer> categoryIdList = new ArrayList<>();
        for (int i = 0; i < categoryEntityList.size(); i++) {
            categoryIdList.add(categoryEntityList.get(i).getId());
        }
        return categoryIdList;
    }
}
