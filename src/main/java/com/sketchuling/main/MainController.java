package com.sketchuling.main;

import jakarta.servlet.http.HttpSession;
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
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("")
    public String main(@RequestParam(value = "date", required = false) String dateStr, Model model) {
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate tue = mon.plusDays(1);
        LocalDate wed = mon.plusDays(2);
        LocalDate thu = mon.plusDays(3);
        LocalDate fri = mon.plusDays(4);
        LocalDate sat = mon.plusDays(5);
        LocalDate sun = mon.plusDays(6);

        // MM/dd 형식의 포맷터 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

        // 날짜를 저장할 HashMap 생성
        Map<String, String> weekDates = new HashMap<>();
        weekDates.put("Monday", mon.format(formatter));
        weekDates.put("Tuesday", tue.format(formatter));
        weekDates.put("Wednesday", wed.format(formatter));
        weekDates.put("Thursday", thu.format(formatter));
        weekDates.put("Friday", fri.format(formatter));
        weekDates.put("Saturday", sat.format(formatter));
        weekDates.put("Sunday", sun.format(formatter));

        model.addAttribute("weekDates", weekDates);
        model.addAttribute("selectedDate", date.format(DateTimeFormatter.ISO_DATE));

        return "main/main";
    }

    @GetMapping("/specific")
    public String specific(){
        return "main/specific";
    }
}
