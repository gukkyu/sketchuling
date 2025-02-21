package com.sketchuling.category.bo;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryBO {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getCategoryListByUserId(int userId) {

        return categoryRepository.findAllByUserId(userId);
    }

    public List<String> getCategoryColorListByUserId(int userId) {
        List<String> colorList = new ArrayList<>();
        colorList.add("#FF0000");
        colorList.add("#FF7F00");
        colorList.add("#FFFF00");
        colorList.add("#00FF00");
        colorList.add("#00FFFF");
        colorList.add("#0000FF");
        colorList.add("#8B00FF");
        colorList.add("#FF1493");
        colorList.add("#FFD700");
        colorList.add("#00FA9A");
        colorList.add("#FF4500");
        colorList.add("#1E90FF");
        colorList.add("#9400D3");
        colorList.add("#32CD32");
        colorList.add("#DC143C");
        colorList.add("#FF69B4");
        colorList.add("#7FFF00");
        colorList.add("#00CED1");

        List<CategoryEntity> userCategoryList = getCategoryListByUserId(userId);
        if(userCategoryList.isEmpty()) {
            return colorList;
        } else {
            for(int i = 0; i < userCategoryList.size(); i++) {
                String color = userCategoryList.get(i).getColor();
                colorList.remove(color);
            }
            return colorList;
        }
    }

    public Map<String, Object> addCategory(int userId, String name, String color) {
        Map<String, Object> result = new HashMap<>();
        try {
            categoryRepository.save(CategoryEntity.builder()
                    .userId(userId)
                    .name(name)
                    .color(color)
                    .build());
            result.put("code", 200);
        } catch (Exception e) {
            result.put("code", 404);
        }

        return result;
    }
}
