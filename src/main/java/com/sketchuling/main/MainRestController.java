package com.sketchuling.main;

import com.sketchuling.category.bo.CategoryBO;
import com.sketchuling.category.entity.CategoryEntity;
import com.sketchuling.main.bo.MainBO;
import com.sketchuling.subcategory.bo.SubcategoryBO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/main")
public class MainRestController {

    private final MainBO mainBO;

    private final CategoryBO categoryBO;

    private final SubcategoryBO subcategoryBO;

    @PostMapping("/updateDates")
    public Map<String, LocalDate> updateWeekDates(@RequestParam("date") String dateStr) {
        Map<String, LocalDate> weekDates = mainBO.getDate(dateStr);

        return weekDates;
    }

    @PostMapping("/addCategory")
    public Map<String, Object> addCategory(@RequestParam("name") String name, @RequestParam("color") String color, HttpSession session) {
        int userId = (int)session.getAttribute("userId");
        return categoryBO.addCategory(userId, name, color);
    }

    @PostMapping("/addSubcategory")
    public Map<String, Object> addSubcategory(@RequestParam("name") String name, @RequestParam("id") String id) {
        int categoryId = Integer.parseInt(id);
        return subcategoryBO.addSubcategory(categoryId, name);
    }
}
