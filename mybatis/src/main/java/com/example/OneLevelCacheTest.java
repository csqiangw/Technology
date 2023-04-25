package com.example;

import com.example.mapper.StudentMapper;
import com.example.model.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class OneLevelCacheTest {

    public SqlSessionFactory factory;

    @Before
    public void before() throws IOException {
        //获取输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        if(inputStream != null){
            inputStream.close();
        }
    }

    //最终没有再次查询，而是命中缓存
    @Test
    public void testOneLevelCache(){
        SqlSession session = factory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        System.out.println("第一次查询");
        Student student = studentMapper.selectById(1022);
        System.out.println(student);
        System.out.println("第二次查询");
        Student student1 = studentMapper.selectById(1022);
        System.out.println(student1);
        if(session != null){
            session.close();
        }
    }

    /**
     * 在同一个连接中，经过一次修改，缓存失效，会重新查询
     */
    @Test
    public void testOneLevelCacheNo(){
        SqlSession session = factory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        System.out.println("第一次查询");
        Student student = studentMapper.selectById(1022);
        System.out.println(student);
        Student newStudent = new Student();
        newStudent.setId(1023);
        newStudent.setName("zbb");
        newStudent.setAge(24);
        newStudent.setTeacherId(11);
        studentMapper.updateById(newStudent);
        session.commit();
        System.out.println("第二次查询");
        Student student1 = studentMapper.selectById(1022);
        System.out.println(student1);
        if(session != null){
            session.close();
        }
    }

    /**
     * 不同连接中，但另外一个连接修改了当前行的数据，此时一级缓存失效，会导致读取脏数据
     */
    @Test
    public void testOneLevelCacheDirty(){
        SqlSession session = factory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        System.out.println("第一次查询");
        Student student = studentMapper.selectById(1022);
        System.out.println(student);

        SqlSession session1 = factory.openSession(true);
        StudentMapper studentMapper1 = session1.getMapper(StudentMapper.class);
        Student newStudent = new Student();
        newStudent.setId(1022);
        newStudent.setName("wq");
        newStudent.setAge(24);
        newStudent.setTeacherId(11);
        studentMapper1.updateById(newStudent);
        session1.commit();

        System.out.println("第二次查询");
        Student student1 = studentMapper.selectById(1022);
        System.out.println(student1);
        if(session != null){
            session.close();
        }
        if(session1 != null){
            session1.close();
        }
    }


}
