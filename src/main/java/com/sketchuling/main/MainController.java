package com.sketchuling.main;

import com.sketchuling.category.bo.CategoryBO;
import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.main.bo.MainBO;
import com.sketchuling.schedule.bo.ScheduleBO;
import com.sketchuling.schedule.domain.Schedule;
import com.sketchuling.subcategory.bo.SubcategoryBO;
import com.sketchuling.subcategory.entity.SubcategoryEntity;
import com.sketchuling.to_do_list.bo.To_do_listBO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Controller
@RequestMapping("/main")
public class MainController {

    private final MainBO mainBO;
    private final ScheduleBO scheduleBO;
    private final To_do_listBO to_do_listBO;
    private final CategoryBO categoryBO;
    private final SubcategoryBO subcategoryBO;

    @GetMapping("")
    public String main(@RequestParam(value = "date", required = false) String dateStr, Model model, HttpSession session) {

        int userId = (int)session.getAttribute("userId");

        Map<String, LocalDate> weekDates = mainBO.getDate(dateStr);
        model.addAttribute("weekDates", weekDates);

        Map<String, List<Schedule>> weekScheduleList = scheduleBO.getWeekScheduleList(userId, dateStr);
        model.addAttribute("weekScheduleList", weekScheduleList);

        List<Map<String, Object>> weekTodolist = to_do_listBO.getToDoListByUserIdAndCreatedAt(dateStr, userId);
        model.addAttribute("weekTodolist", weekTodolist);


        return "main/main";
    }

    @GetMapping("/specific")
    public String specific(@RequestParam("date") LocalDate date, Model model, HttpSession session) {
        int userId = (int)session.getAttribute("userId");
        String dateStr = date.toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.KOREAN);
        String editedDay = dayName.substring(0, 1);
        model.addAttribute("date", date);
        model.addAttribute("dayName", editedDay);

        List<Schedule> dayScheduleList = scheduleBO.getDayScheduleList(userId, dateStr);
        model.addAttribute("dayScheduleList", dayScheduleList);

        List<Map<String, Object>> dayTodolist = to_do_listBO.getToDoListByCategoryIdAndCreatedAt(dateStr, userId, dayScheduleList);
        model.addAttribute("dayTodolist", dayTodolist);


        return "main/specific";
    }

    @GetMapping("/specific/addSchedule")
    public String specificAddSchedule(HttpSession session, Model model) {
        List<CategoryEntity> categoryList = categoryBO.getCategoryListByUserId((int)session.getAttribute("userId"));
        if(categoryBO.getCategoryListByUserId((int)session.getAttribute("userId")).isEmpty()) {
            return "redirect:/main/specific/addCategory";
        }
        model.addAttribute("categoryList", categoryList);
        return "main/addSchedule";
    }
    @GetMapping("/specific/addTodolist")
    public String specificAddTodolist(HttpSession session, Model model, int categoryId, String startTime, String endTime) {
        List<SubcategoryEntity> subcategoryList = subcategoryBO.getSubcategoryListByCategoryId(categoryId);
        CategoryEntity category = categoryBO.getCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("subcategoryList", subcategoryList);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "main/addTodolist";
    }

    @GetMapping("/specific/addCategory")
    public String specificAddCategory(HttpSession session, Model model) {
        List<String> colorList = categoryBO.getCategoryColorListByUserId((int)session.getAttribute("userId"), (List<String>)session.getAttribute("categoryColorList"));
        model.addAttribute("colorList", colorList);
        return "main/addCategory";
    }

    @GetMapping("/specific/addSubcategory")
    public String specificAddSubCategory(HttpSession session, Model model) {
        List<CategoryEntity> categoryList = categoryBO.getCategoryListByUserId((int)session.getAttribute("userId"));
        if(categoryList.isEmpty()) {
            return "redirect:/main/specific/addCategory";
        }
        model.addAttribute("categoryList", categoryList);
        return "main/addSubcategory";
    }
}
