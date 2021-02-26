package com.example.xks.controller;

import com.example.xks.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    @RequestMapping("/login")
    public boolean login(@RequestParam(value="sid", required = true) String sid,
                                        @RequestParam(value="spassword", required = true) String spassword){
        boolean rtn=false;
        try{
            rtn=studentService.login(sid,spassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("login...");
        return rtn;
    }
}
