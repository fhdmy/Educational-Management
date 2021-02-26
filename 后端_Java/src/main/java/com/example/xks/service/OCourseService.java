package com.example.xks.service;

import com.example.xks.ServerApplication;
import com.example.xks.entity.ChooseCourse;
import com.example.xks.entity.OCourse;
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
public class OCourseService {
    @Transactional
    public List<OCourse> getOCourse(String sid,String cid,String cname,String score,String tid,String tname){
        OCourse queryCourse=new OCourse(cid,cname,score,tid,tname);
        String resource="mybatis-config.xml";
        InputStream is = ServerApplication.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        SqlSession session=sessionFactory.openSession();
        String statement = "com.example.xks.mapper.OCourseMapper.getOCourse";
        String judgestatement = "com.example.xks.mapper.ChooseCourseMapper.judgeChosen";
        List<OCourse> oCourses=null;
        try{
            oCourses = session.selectList(statement,queryCourse);
            for (OCourse oc:oCourses) {
                ChooseCourse qc=new ChooseCourse(sid,oc.getCid(),oc.getTid(),oc.getTime());
                ChooseCourse chooseCourse = session.selectOne(judgestatement,qc);
                if(chooseCourse!=null)
                    oc.setChosen(true);
                else
                    oc.setChosen(false);
            }
        }finally {
            session.close();
        }
        return oCourses;
    }

    @Transactional
    public List<OCourse> getChooseCourse(String sid){
        String resource="mybatis-config.xml";
        InputStream is = ServerApplication.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        SqlSession session=sessionFactory.openSession();
        String statement = "com.example.xks.mapper.OCourseMapper.getChooseCourse";
        List<OCourse> oCourses=null;
        try{
            oCourses = session.selectList(statement,sid);
        }finally {
            session.close();
        }
        return oCourses;
    }
}
