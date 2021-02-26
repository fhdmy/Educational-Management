package com.example.xks.mapper;

import com.example.xks.entity.OCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OCourseMapper {
    List<OCourse> getOCourse(@Param("cid") String cid,
                             @Param("cname") String cname,
                             @Param("score") int score,
                             @Param("tid") String tid,
                             @Param("tname") String tname);
    List<OCourse> getChooseCourse(@Param("sid") String sid);

    void addChosenNum(@Param("tid") String tid,
                      @Param("cid") String cid,
                      @Param("time") String time);

    void deleteChosenNum(@Param("tid") String tid,
                         @Param("cid") String cid,
                         @Param("time") String time);
}
