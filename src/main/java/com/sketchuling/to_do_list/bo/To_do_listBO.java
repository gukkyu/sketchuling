package com.sketchuling.to_do_list.bo;

import com.sketchuling.category.bo.CategoryBO;
import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.schedule.domain.Schedule;
import com.sketchuling.subcategory.bo.SubcategoryBO;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import com.sketchuling.to_do_list.domain.to_do_list;
import com.sketchuling.to_do_list.mapper.To_do_listMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class To_do_listBO {

    private final To_do_listMapper to_do_listMapper;

    public List<Map<String, Object>> getToDoListByUserIdAndCreatedAt(String dateStr, int userId) {
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String monday = mon.toString();
        LocalDate nextMon = mon.plusDays(7);
        String nextMonday = nextMon.toString();

        List<Map<String, Object>> rawList = to_do_listMapper.selectToDoListByUserIdAndCreatedAt(userId, monday, nextMonday);

        Map<String, Map<String, Object>> categoryMap = new LinkedHashMap<>();

        for (Map<String, Object> row : rawList) {
            String categoryName = (String) row.get("categoryName");
            String categoryColor = (String) row.get("categoryColor");
            String subcategoryName = (String) row.get("subcategoryName");

            if (!categoryMap.containsKey(categoryName)) {
                Map<String, Object> category = new HashMap<>();
                category.put("category", categoryName);
                category.put("color", categoryColor);
                category.put("tasks", new ArrayList<Map<String, Object>>());
                category.put("subcategories", new ArrayList<Map<String, Object>>());
                categoryMap.put(categoryName, category);
            }

            Map<String, Object> category = categoryMap.get(categoryName);
            List<Map<String, Object>> categoryTasks = (List<Map<String, Object>>) category.get("tasks");
            if (subcategoryName == null) {
                categoryTasks.add(extractTask(row));
            }

            List<Map<String, Object>> subcategories = (List<Map<String, Object>>) category.get("subcategories");

            if (subcategoryName != null) {
                Map<String, Object> subcategory = subcategories.stream()
                        .filter(sc -> sc.get("subcategory").equals(subcategoryName))
                        .findFirst()
                        .orElseGet(() -> {
                            Map<String, Object> newSubcategory = new HashMap<>();
                            newSubcategory.put("subcategory", subcategoryName);
                            newSubcategory.put("tasks", new ArrayList<Map<String, Object>>());
                            subcategories.add(newSubcategory);
                            return newSubcategory;
                        });
                ((List<Map<String, Object>>) subcategory.get("tasks")).add(extractTask(row));
            }
        }
        return new ArrayList<>(categoryMap.values());
    }


    public List<Map<String, Object>> getToDoListByCategoryIdAndCreatedAt(String dateStr, int userId, List<Schedule> dayScheduleList) {
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String monday = mon.toString();
        LocalDate nextMon = mon.plusDays(7);
        String nextMonday = nextMon.toString();

        Set<Integer> dayCategoryIdSet = dayScheduleList.stream()
                .map(Schedule::getCategoryId)
                .collect(Collectors.toSet());
        List<Integer> dayCategoryIdList = new ArrayList<>(dayCategoryIdSet);

        List<Map<String, Object>> rawList = to_do_listMapper.selectToDoListByCategoryIdAndCreatedAt(dayCategoryIdList, monday, nextMonday);

        Map<String, Map<String, Object>> categoryMap = new LinkedHashMap<>();
        for (Map<String, Object> row : rawList) {
            String categoryName = (String) row.get("categoryName");
            String categoryColor = (String) row.get("categoryColor");
            String subcategoryName = (String) row.get("subcategoryName");

            categoryMap.putIfAbsent(categoryName, new HashMap<>());
            Map<String, Object> category = categoryMap.get(categoryName);
            category.putIfAbsent("category", categoryName);
            category.putIfAbsent("color", categoryColor);
            category.putIfAbsent("tasks", new ArrayList<>());
            category.putIfAbsent("subcategories", new ArrayList<>());

            if (subcategoryName == null) {
                ((List<Map<String, Object>>) category.get("tasks")).add(extractTask(row));
            } else {
                List<Map<String, Object>> subcategories = (List<Map<String, Object>>) category.get("subcategories");
                Map<String, Object> subcategory = subcategories.stream()
                        .filter(sc -> sc.get("subcategory").equals(subcategoryName))
                        .findFirst()
                        .orElseGet(() -> {
                            Map<String, Object> newSub = new HashMap<>();
                            newSub.put("subcategory", subcategoryName);
                            newSub.put("tasks", new ArrayList<>());
                            subcategories.add(newSub);
                            return newSub;
                        });
                ((List<Map<String, Object>>) subcategory.get("tasks")).add(extractTask(row));
            }
        }
        return new ArrayList<>(categoryMap.values());
    }

    private Map<String, Object> extractTask(Map<String, Object> row) {
        Map<String, Object> task = new HashMap<>();
        task.put("id", row.get("id"));
        task.put("todolist", row.get("todolist"));
        task.put("isChecked", row.get("isChecked"));
        return task;
    }

    public int updateCheckByTodolistId(int id, boolean isChecked){
        return to_do_listMapper.updateCheckByTodolistId(id, isChecked);
    }
}
