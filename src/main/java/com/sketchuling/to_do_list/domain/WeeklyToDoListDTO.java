package com.sketchuling.to_do_list.domain;

import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import com.sketchuling.to_do_list.bo.To_do_listBO;
import lombok.Data;

@Data
public class WeeklyToDoListDTO {

    private to_do_list toDoList;

    private CategoryEntity categoryEntity;

    private SubcategoryEntity subcategoryEntity;
}
