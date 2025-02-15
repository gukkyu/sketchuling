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

@Service
@AllArgsConstructor
public class To_do_listBO {

    private final To_do_listMapper to_do_listMapper;
    private final CategoryBO categoryBO;
    private final SubcategoryBO subcategoryBO;

    public List<Map<String, Object>> getToDoListByUserIdAndCreatedAt(String dateStr, int userId) {
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String monday = mon.toString();
        LocalDate nextMon = mon.plusDays(7);
        String nextMonday = nextMon.toString();

        List<Map<String, Object>> rawList = to_do_listMapper.selectToDoListByUserIdAndCreatedAt(userId, monday, nextMonday);

        Map<String, Map<String, Object>> categoryMap = new LinkedHashMap<>();

        for (int i = 0; i < rawList.size(); i++) {
            Map<String, Object> row = rawList.get(i);
            String categoryName = (String) row.get("categoryName");
            String subcategoryName = (String) row.get("subcategoryName");

            if (!categoryMap.containsKey(categoryName)) {
                Map<String, Object> category = new HashMap<>();
                category.put("category", categoryName);
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
                Map<String, Object> subcategory = null;
                for (int j = 0; j < subcategories.size(); j++) {
                    if (subcategories.get(j).get("subcategory").equals(subcategoryName)) {
                        subcategory = subcategories.get(j);
                        break;
                    }
                }

                if (subcategory == null) {
                    subcategory = new HashMap<>();
                    subcategory.put("subcategory", subcategoryName);
                    subcategory.put("tasks", new ArrayList<Map<String, Object>>());
                    subcategories.add(subcategory);
                }

                List<Map<String, Object>> subTasks = (List<Map<String, Object>>) subcategory.get("tasks");
                subTasks.add(extractTask(row));
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


    public List<Map<String, Object>> getToDoListByCategoryIdAndCreatedAt(String dateStr, int userId, List<Schedule> dayScheduleList){
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String monday = mon.toString();
        LocalDate nextMon = mon.plusDays(7);
        String nextMonday = nextMon.toString();

        List<Integer> dayCategoryList = new ArrayList<>();
        for(int i = 0; i < dayScheduleList.size(); i++) {
            int categoryId = dayScheduleList.get(i).getCategoryId();
            dayCategoryList.add(categoryId);
        }
        Set<Integer> dayCategoryIdSet = new HashSet<>(dayCategoryList);
        List<Integer> dayCategoryIdList = new ArrayList<>(dayCategoryIdSet);

        return to_do_listMapper.selectToDoListByCategoryIdAndCreatedAt(dayCategoryIdList, monday, nextMonday);
    }
}
