package com.example.xks.controller;


import com.example.xks.service.ChooseCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chooseCourse")
public class ChooseCourseController {

    private ChooseCourseService chooseCourseService;

    @Autowired
    public ChooseCourseController(ChooseCourseService chooseCourseService){
        this.chooseCourseService=chooseCourseService;
    }

    @RequestMapping(value = "/chooseCourse",method = RequestMethod.POST)
    public void chooseCourse(@RequestBody Map<String, Object> params){
        try{
            chooseCourseService.chooseCourse(params.get("sid").toString(),
                                            params.get("cid").toString(),
                                            params.get("tid").toString(),
                                            params.get("time").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("chooseCourse...");
    }

    @RequestMapping(value = "/cancelCourse",method = RequestMethod.POST)
    public void cancelCourse(@RequestBody Map<String, Object> params){
        try{
            chooseCourseService.cancelCourse(params.get("sid").toString(),
                    params.get("cid").toString(),
                    params.get("tid").toString(),
                    params.get("time").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("cancelCourse...");
    }
}
