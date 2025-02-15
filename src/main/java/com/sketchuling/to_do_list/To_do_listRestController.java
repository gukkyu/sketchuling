package com.sketchuling.to_do_list;

import com.sketchuling.to_do_list.bo.To_do_listBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/todolist")
public class To_do_listRestController {

    @Autowired
    To_do_listBO to_do_listBO;

    @PatchMapping("/checking")
    public Map<String, Object> checking(@RequestParam("id") int id, @RequestParam("isChecked") boolean isChecked) {
        int rowCount = to_do_listBO.updateCheckByTodolistId(id, !isChecked);
        Map<String, Object> result = new HashMap<>();
        if(rowCount > 0) {
            result.put("code", 200);
            result.put("result", "success");
        } else {
            result.put("code", 404);
            result.put("result", "fail");
        }
        return result;
    }
}
