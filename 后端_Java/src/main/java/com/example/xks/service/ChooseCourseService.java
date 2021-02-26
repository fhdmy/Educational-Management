package com.example.xks.service;

import com.example.xks.ServerApplication;
import com.example.xks.entity.ChooseCourse;
import com.example.xks.entity.OCourse;
import com.example.xks.entity.OfferCourse;
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
public class ChooseCourseService {
    @Transactional
    public void chooseCourse(String sid,String cid,String tid,String time){
        String resource="mybatis-config.xml";
        InputStream is = ServerApplication.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        SqlSession session=sessionFactory.openSession();
        ChooseCourse chooseCourse=new ChooseCourse(sid,cid,tid,time);
        OfferCourse offerCourse=new OfferCourse(cid,tid,time);
        String statement = "com.example.xks.mapper.ChooseCourseMapper.chooseCourse";
        String addstatement="com.example.xks.mapper.OCourseMapper.addChosenNum";
        try{
            session.insert(statement,chooseCourse);
            session.update(addstatement,offerCourse);
            session.commit();
        }finally {
            session.close();
        }
    }

    @Transactional
    public void cancelCourse(String sid,String cid,String tid,String time){
        String resource="mybatis-config.xml";
        InputStream is = ServerApplication.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(is);
        SqlSession session=sessionFactory.openSession();
        ChooseCourse chooseCourse=new ChooseCourse(sid,cid,tid,time);
        OfferCourse offerCourse=new OfferCourse(cid,tid,time);
        String statement = "com.example.xks.mapper.ChooseCourseMapper.cancelCourse";
        String deletestatement="com.example.xks.mapper.OCourseMapper.deleteChosenNum";
        try{
            session.delete(statement,chooseCourse);
            session.update(deletestatement,offerCourse);
            session.commit();
        }finally {
            session.close();
        }
    }
}


