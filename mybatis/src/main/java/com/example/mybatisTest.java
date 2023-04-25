package com.example;

import com.example.mapper.StudentMapper;
import com.example.model.Student;
import com.example.model.Teacher;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class mybatisTest {

    public StudentMapper studentMapper;

    public SqlSession session;

    @Before
    public void before() throws IOException {
        //获取输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        session = factory.openSession(true);
        studentMapper = session.getMapper(StudentMapper.class);
        if(inputStream != null){
            inputStream.close();
        }
    }

    @Test
    public void testPage(){
        //TODO 原理待看
        PageHelper.startPage(2,10);
        List<Student> students = studentMapper.selectAll();
        System.out.println(students.size());
        PageInfo<Student> pageInfo = new PageInfo<>(students);//获取更详细的信息
        System.out.println(pageInfo.getTotal());//获取总条数
        System.out.println(pageInfo.getPageNum());//获取总页数
        System.out.println(pageInfo.getPageNum());//获取当前页是第几页
    }

    @Test
    public void testSelectById(){
        //可以通过session提供的一些方法，也可以通过获取自定义的mapper来调用方法
//        session.selectOne("StudentMapper.selectById",1);
        Student student = studentMapper.selectById(1);
        System.out.println(student);
    }

    @Test
    public void testUpdateById(){
        Student student = new Student();
        student.setId(1);
        student.setName("wq");
        student.setAge(24);
        student.setTeacherId(11);
        System.out.println(studentMapper.updateById(student));
    }

    @Test
    public void testDeleteById(){
        System.out.println(studentMapper.deleteById(2));
    }

    @Test
    public void testInsertOne(){
        Student student = new Student();
        student.setName("zbb");
        student.setAge(24);
        student.setTeacherId(11);
        System.out.println(studentMapper.insertOne(student));
    }

    @Test
    public void testSelectByName(){
        System.out.println(studentMapper.selectByName(null).size());
    }

    @Test
    public void testSelectByIds(){
        List<Integer> ids = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};
        System.out.println(studentMapper.selectByIds(ids));
    }

    @Test
    public void testOneToMany(){
        Teacher teacher = studentMapper.selectTeacherById(11);
        System.out.println(teacher);
    }

    @Test
    public void testSelectPersonById(){
        System.out.println(studentMapper.selectPersonById(4));
    }

    @After
    public void after(){
        if(session != null){
            session.commit(true);
            session.close();
        }
    }

}
