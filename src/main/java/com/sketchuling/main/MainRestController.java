package com.sketchuling.main;

import com.sketchuling.main.bo.MainBO;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/main")
public class MainRestController {

    private final MainBO mainBO;

    @PostMapping("/updateDates")
    public Map<String, LocalDate> updateWeekDates(@RequestParam("date") String dateStr) {

        Map<String, LocalDate> weekDates = mainBO.getDate(dateStr);

        return weekDates;
    }
}
