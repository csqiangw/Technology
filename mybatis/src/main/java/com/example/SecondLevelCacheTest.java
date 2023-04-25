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

public class SecondLevelCacheTest {

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

    //测试二级缓存
    @Test
    public void testSecondLevelCache(){
        SqlSession session1 = factory.openSession();
        SqlSession session2 = factory.openSession();
        StudentMapper studentMapper1 = session1.getMapper(StudentMapper.class);
        StudentMapper studentMapper2 = session2.getMapper(StudentMapper.class);
        Student student1 = studentMapper1.selectById(1022);
        System.out.println(student1);
        session1.commit();//必须提交之后才会生效
        Student student2 = studentMapper2.selectById(1022);
        System.out.println(student2);
        session2.commit();
        if(session1 != null){
            session1.close();
        }
        if(session2 != null){
            session2.close();
        }
    }

    //测试二级缓存是否存在脏数据
    @Test
    public void testSecondLevelCacheDirty(){
        SqlSession session1 = factory.openSession();
        SqlSession session2 = factory.openSession();
        StudentMapper studentMapper1 = session1.getMapper(StudentMapper.class);
        StudentMapper studentMapper2 = session2.getMapper(StudentMapper.class);
        Student student1 = studentMapper1.selectById(1022);
        System.out.println(student1);
        session1.commit();//必须提交之后才会生效

        //修改后再次查询
        Student newStudent = new Student();
        newStudent.setId(1023);
        newStudent.setName("zbbsss");
        newStudent.setAge(23);
        newStudent.setTeacherId(11);
        studentMapper2.updateById(newStudent);
        session2.commit();

        student1 = studentMapper1.selectById(1022);
        System.out.println(student1);
        session1.commit();//必须提交之后才会生效
        if(session1 != null){
            session1.close();
        }
        if(session2 != null){
            session2.close();
        }
    }

}
