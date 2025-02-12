package com.sketchuling.schedule.bo;

import com.sketchuling.schedule.domain.Schedule;
import com.sketchuling.schedule.mapper.ScheduleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ScheduleBO {
    private final ScheduleMapper scheduleMapper;
    public Map<String, List<Schedule>> getWeekScheduleList(int userId, String dateStr) {
        Map<String, List<Schedule>> week = new HashMap<>();

        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();

        LocalDate monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (int i = 0; i < 7; i++) {
            LocalDate day = monday.plusDays(i);
            String dayStr = day.toString();
            String key = day.getDayOfWeek().toString().toLowerCase();

            week.put(key, scheduleMapper.selectScheduleListByUserIdAndDate(userId, dayStr));
        }

        return week;
    }


}
