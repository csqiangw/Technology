package com.example.mapper;

import com.example.model.Person;
import com.example.model.Student;
import com.example.model.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {

    List<Student> selectAll();

    Student selectById(@Param("id") int id);

    int updateById(Student student);

    int deleteById(@Param("id") int id);

    int insertOne(Student student);

    List<Student> selectByName(@Param("name") String name);

    List<Student> selectByIds(@Param("ids") List<Integer> ids);

    Teacher selectTeacherById(@Param("id") int id);

    Person selectPersonById(@Param("id") int id);

}
