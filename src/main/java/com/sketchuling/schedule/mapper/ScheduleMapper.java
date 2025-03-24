package com.sketchuling.schedule.mapper;

import com.sketchuling.schedule.domain.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {
    public List<Schedule> selectScheduleListByUserIdAndDate(
            @Param("userId") int userId,
            @Param("date") String date);

    public int insertSchedule(@Param("userId") int userId,
                              @Param("categoryId") int categoryId,
                              @Param("startTime") LocalDateTime startDateTime,
                              @Param("endTime") LocalDateTime endDateTime);
}
