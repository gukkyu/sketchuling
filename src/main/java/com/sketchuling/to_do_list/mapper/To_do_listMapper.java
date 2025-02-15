package com.sketchuling.to_do_list.mapper;

import com.sketchuling.to_do_list.domain.to_do_list;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface To_do_listMapper {
    public List<to_do_list> selectTodolistByCategoryId(
            @Param("categoryId") int categoryId,
            @Param("start") String start,
            @Param("end") String end);

    public List<to_do_list> selectTodolistBySubcategoryId(
            @Param("subcategoryId") int subcategoryId,
            @Param("start") String start,
            @Param("end") String end);

    public List<Map<String, Object>> selectToDoListByUserIdAndCreatedAt(
            @Param("userId") int userId,
            @Param("start") String start,
            @Param("end") String end);

    public List<Map<String, Object>> selectToDoListByCategoryIdAndCreatedAt(
            @Param("categoryIdList") List<Integer> categoryIdList,
            @Param("start") String start,
            @Param("end") String end);
}
