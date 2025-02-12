package com.sketchuling.main;

import com.sketchuling.main.bo.MainBO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

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
