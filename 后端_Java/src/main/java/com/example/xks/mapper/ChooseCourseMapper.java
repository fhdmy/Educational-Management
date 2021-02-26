package com.example.xks.mapper;

import com.example.xks.entity.ChooseCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChooseCourseMapper {
    void chooseCourse(@Param("sid") String sid,
                    @Param("cid") String cid,
                    @Param("tid") String tid,
                    @Param("time") String time);

    void cancelCourse(@Param("sid") String sid,
                      @Param("cid") String cid,
                      @Param("tid") String tid,
                      @Param("time") String time);
    List<ChooseCourse> judgeChosen(@Param("sid") String sid,
                                   @Param("cid") String cid,
                                   @Param("tid") String tid,
                                   @Param("time") String time);
}
