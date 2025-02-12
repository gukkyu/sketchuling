package com.sketchuling.to_do_list.bo;

import com.sketchuling.to_do_list.mapper.To_do_listMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class To_do_listBO {

    private final To_do_listMapper to_do_listMapper;

    public List<Map<String, Object>> getToDoListByUserIdAndCreatedAt(String dateStr, int userId){
        LocalDate date = (dateStr != null) ? LocalDate.parse(dateStr) : LocalDate.now();
        LocalDate mon = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String monday = mon.toString();
        LocalDate nextMon = mon.plusDays(7);
        String nextMonday = nextMon.toString();
        return to_do_listMapper.selectToDoListByUserIdAndCreatedAt(userId, monday, nextMonday);
    }
}
