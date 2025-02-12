package com.sketchuling.main.domain;

import com.sketchuling.category.domain.Category;
import com.sketchuling.schedule.domain.Schedule;
import com.sketchuling.subcategory.domain.Subcategory;
import com.sketchuling.to_do_list.domain.to_do_list;
import com.sketchuling.user.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class MainDTO {
    private UserEntity user;
    private List<Schedule> schedule;
    private List<Category> category;
    private List<Subcategory> subcategory;
    private List<to_do_list> toDoList;
}
