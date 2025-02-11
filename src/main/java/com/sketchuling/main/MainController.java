package com.sketchuling.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("")
    public String main(){

        LocalDate date = LocalDate.now();
        // 해당 날짜의 주 시작일 (월요일)
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 해당 날짜의 주 끝일 (일요일)
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return "main/main";
    }

    @GetMapping("/specific")
    public String specific(){
        return "main/specific";
    }
}
