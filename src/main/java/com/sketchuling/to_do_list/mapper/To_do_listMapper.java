package com.sketchuling.to_do_list.mapper;

import com.sketchuling.to_do_list.domain.to_do_list;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface To_do_listMapper {

    public List<Map<String, Object>> selectToDoListByUserIdAndCreatedAt(
            @Param("userId") int userId,
            @Param("start") String start,
            @Param("end") String end);

    public List<Map<String, Object>> selectToDoListByCategoryIdAndCreatedAt(
            @Param("categoryIdList") List<Integer> categoryIdList,
            @Param("start") String start,
            @Param("end") String end);

    public int updateCheckByTodolistId(
              @Param("id") int id,
              @Param("isChecked") boolean isChecked
    );

    public int insertTodolist(
            @Param("categoryId") int categoryId,
            @Param("subcategoryId") Integer subcategoryId,
            @Param("todolist") String todolist,
            @Param("isChecked") boolean isChecked
            );
}
