package com.sketchuling.subcategory.bo;

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

    public List<Integer> getSubcategoryIdListByCategoryId(int categoryId) {

        List<SubcategoryEntity> subcategoryEntityList = subcategoryRepository.findAllByCategoryId(categoryId);
        List<Integer> subcategoryIdList = new ArrayList<>();
        for (int i = 0; i < subcategoryEntityList.size(); i++) {
            subcategoryIdList.add(subcategoryEntityList.get(i).getId());
        }
        return subcategoryIdList;
    }
}
