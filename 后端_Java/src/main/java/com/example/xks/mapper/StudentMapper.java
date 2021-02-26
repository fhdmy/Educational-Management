package com.example.xks.mapper;

import com.example.xks.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentMapper {
    Student login(@Param("sid") String sid);
}
