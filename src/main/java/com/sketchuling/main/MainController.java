package com.sketchuling.main;

import com.sketchuling.main.bo.MainBO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/main")
public class MainController {

    private final MainBO mainBO;

    @GetMapping("")
    public String main(@RequestParam(value = "date", required = false) String dateStr, Model model) {

        Map<String, LocalDate> weekDates = mainBO.getDate(dateStr);

        model.addAttribute("weekDates", weekDates);

        return "main/main";
    }

    @GetMapping("/specific")
    public String specific(@RequestParam("date") LocalDate date, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.KOREAN);
        String editedDay = dayName.substring(0, 1);

        model.addAttribute("date", date);
        model.addAttribute("dayName", editedDay);
        return "main/specific";
    }
}
