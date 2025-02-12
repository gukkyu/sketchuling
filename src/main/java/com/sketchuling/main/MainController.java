package com.sketchuling.main;

import com.sketchuling.main.bo.MainBO;
import com.sketchuling.schedule.bo.ScheduleBO;
import com.sketchuling.schedule.domain.Schedule;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/main")
public class MainController {

    private final MainBO mainBO;
    private final ScheduleBO scheduleBO;
    private final To_do_listBO to_do_listBO;

    @GetMapping("")
    public String main(@RequestParam(value = "date", required = false) String dateStr, Model model, HttpSession session) {

        int userId = (int)session.getAttribute("userId");

        Map<String, LocalDate> weekDateList = mainBO.getDate(dateStr);
        model.addAttribute("weekDateList", weekDateList);

        Map<String, List<Schedule>> weekScheduleList = scheduleBO.getWeekScheduleList(userId, dateStr);
        model.addAttribute("weekScheduleList", weekScheduleList);

        List<Map<String, Object>> weekTodolist = to_do_listBO.getToDoListByUserIdAndCreatedAt(dateStr, userId);
        model.addAttribute("weekTodolist", weekTodolist);


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
