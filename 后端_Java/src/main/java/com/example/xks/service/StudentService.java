package com.example.xks.service;

import com.example.xks.ServerApplication;
import com.example.xks.entity.OCourse;
import com.example.xks.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class StudentService {
    @Transactional
    public boolean login(String sid,String spassword){
        String resource="mybatis-config.xml";
        InputStream is = ServerApplication.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        SqlSession session=sessionFactory.openSession();
        String statement = "com.example.xks.mapper.StudentMapper.login";
        Student student=null;
        try{
            student = session.selectOne(statement,sid);
        }finally {
            session.close();
        }
        boolean rtn = false;
        if(student.getSpassword().equals(spassword))
        {
            rtn=true;
        }
        return rtn;
    }
}


