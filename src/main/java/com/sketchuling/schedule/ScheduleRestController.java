package com.sketchuling.schedule;

import com.sketchuling.schedule.bo.ScheduleBO;
import com.sketchuling.to_do_list.bo.To_do_listBO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ScheduleRestController {

    private final ScheduleBO scheduleBO;
    private final To_do_listBO todolistBO;

    @PostMapping("/schedule/add")
    public Map<String, Object> addSchedule(HttpSession session,
                                           @RequestParam("categoryId") int categoryId,
                                           @RequestParam("subcategoryId") Integer subcategoryId,
                                           @RequestParam("startTime") String startTime,
                                           @RequestParam("endTime") String endTime,
                                           @RequestParam("todolist") String todolist
                                           ){
        Map<String, Object> result = new HashMap<String, Object>();
        int userId = (int)session.getAttribute("userId");
        if(subcategoryId == 0){
            subcategoryId = null;
        }
        int scheduleRowCount = 0;
        int todolistRowCount = 0;
        if(todolist.isEmpty()){
            scheduleRowCount = scheduleBO.addSchedule(userId, categoryId, startTime, endTime);
            todolistRowCount = 1;
        } else{
            scheduleRowCount = scheduleBO.addSchedule(userId, categoryId, startTime, endTime);
            todolistRowCount = todolistBO.addTodolist(categoryId, subcategoryId, todolist);
        }

        if(scheduleRowCount > 0 && todolistRowCount > 0){
            result.put("code", 200);
            result.put("result", "성공");
        } else{
            result.put("code", 404);
            result.put("error_message", "관리자에게 문의 바랍니다.");
        }

        return result;
    }
}
