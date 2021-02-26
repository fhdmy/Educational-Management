package com.example.xks.controller;


import com.example.xks.entity.OCourse;
import com.example.xks.service.OCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ocourse")
public class OCourseController {

    private OCourseService oCourseService;

    @Autowired
    public OCourseController(OCourseService oCourseService){
        this.oCourseService=oCourseService;
    }

    @RequestMapping("/getOCourse")
    public List<OCourse> getOCourse(@RequestParam(value="sid", required = true) String sid,
                              @RequestParam(value="cid", required = true) String cid,
                              @RequestParam(value="cname", required = true) String cname,
                              @RequestParam(value="score", required = true) String score,
                              @RequestParam(value="tid", required = true) String tid,
                              @RequestParam(value="tname", required = true) String tname){
        List<OCourse> oCourse=null;
        try{
            oCourse=oCourseService.getOCourse(sid,cid,cname,score,tid,tname);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("getOCourse...");
        return oCourse;
    }

    @RequestMapping("/getChooseCourse")
    public List<OCourse> login(@RequestParam(value="sid", required = true) String sid){
        List<OCourse> oCourses=null;
        try{
            oCourses=oCourseService.getChooseCourse(sid);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("getChooseCourse...");
        return oCourses;
    }
}
