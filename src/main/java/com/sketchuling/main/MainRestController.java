package com.sketchuling.main;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainRestController {

    @PostMapping("/updateDates")
    public Map<String, String> updateWeekDates(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);

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

        return weekDates;
    }
}
