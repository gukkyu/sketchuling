package com.sketchuling.main.bo;

import com.sketchuling.main.domain.MainDTO;
import com.sketchuling.subcategory.domain.Subcategory;
import com.sketchuling.user.bo.UserBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class MainBO {

    private final UserBO userBO;
    private final ScheduleBO scheduleBO;
    private final CategoryBO categoryBO;
    private final SubcategoryBO subcategoryBO;
    private final To_do_listBO to_do_listBO;

    public MainDTO generateMainDTO(int userId) {
        MainDTO mainDTO = new MainDTO();
        return mainDTO;
    }

    public Map<String, LocalDate> getDate(String dateStr){
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate tue = mon.plusDays(1);
        LocalDate wed = mon.plusDays(2);
        LocalDate thu = mon.plusDays(3);
        LocalDate fri = mon.plusDays(4);
        LocalDate sat = mon.plusDays(5);
        LocalDate sun = mon.plusDays(6);

        Map<String, LocalDate> weekDates = new HashMap<>();
        weekDates.put("Monday", mon);
        weekDates.put("Tuesday", tue);
        weekDates.put("Wednesday", wed);
        weekDates.put("Thursday", thu);
        weekDates.put("Friday", fri);
        weekDates.put("Saturday", sat);
        weekDates.put("Sunday", sun);

        return weekDates;
    }
}
