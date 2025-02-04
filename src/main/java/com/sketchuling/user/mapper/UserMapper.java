package com.sketchuling.user.mapper;

import com.sketchuling.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getUserById(int id);
}
