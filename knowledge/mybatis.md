# mybatis

## 1.如何使用

1.配置文件

mybatisConfig.xml	log4j.properties	jdbc.properties

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>

    <!--引入数据库配置文件  k v 键值对-->
    <properties resource="jdbc.properties"/>

    <!--配置log4j-->
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>

    <!--起别名-->
    <typeAliases>
        <package name="com.example.model"/>
    </typeAliases>

    <!--配置插件PageHelper-->
    <plugins>
        <!--pagehelper4.0后不用指定方言，且拦截器需要配置拦截器-->
        <plugin interceptor="com.github.pagehelper.PageInterceptor"/>
    </plugins>

    <!--配置环境列表，每个environment代表一个环境，default配置默认选择哪个环境-->
    <environments default="mysql">
        <environment id="mysql">
            <!--type代表使用事物的种类
            1.JDBC：全部使用jdbc的事物管理
            2.Managed：不使用事物管理，也从不提交-->
            <transactionManager type="JDBC"/>
            <!--type代表mybatis获取数据库连接的方式
            1.unPooled 非线程池获取，直接获取一个连接
            2.pooled 使用线程池-->
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <!--这面要写文件路径-->
        <mapper resource="com/example/mapper/StudentMapper.xml"/>
    </mappers>

</configuration>
```

```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,stdout

#控制台输出的相关设置
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.Encoding=UTF-8
log4j.appender.console.layout.ConversionPattern=%5p [%t] - %m%n
```

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://xxx?xxx
username=root
password=xxx
```

2.操作步骤

![截屏2023-04-25 13.05.56](/Users/qiangw/Desktop/截屏2023-04-25 13.05.56.png)

需要注意映射关系

```java
//inputStream需要关闭，session连接也需要关闭
//1.利用Resources以流的方式读取配置文件
InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
//使用输入流构建连接创建工厂
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
//打开一个与数据库的连接
//true代表事物自动提交
//false代表不自动提交，需要手动提交
session = factory.openSession(true);
//获取mapper
//也可以通过mybatis提供的一些简便的方法
studentMapper = session.getMapper(StudentMapper.class);
//接下来使用mapper类中的方法进行crud即可
```

StudentMapper.java

```java
//注意包的关键
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
```

StudentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--如果使用获取mapper的方式，这里要与接口mapper一一对应-->
<mapper namespace="com.example.mapper.StudentMapper">
    <!--对于公共的sql，可以通过sql抽取 include引入-->
    <sql id="selectCommon">select * from student</sql>

    <!--查询全部，分页测试-->
    <select id="selectAll" resultType="Student">
        <include refid="selectCommon"/>
    </select>

    <!--按照单id查询-->
    <select id="selectById" parameterType="int" resultType="Student">
        <include refid="selectCommon"/> where id = #{id}
    </select>

    <!--按照单id更新-->
    <update id="updateById" parameterType="Student">
        update student set name = #{name},age = #{age},teacher_id = #{teacherId} where id = #{id}
    </update>

    <!--按照单id删除-->
    <delete id="deleteById" parameterType="int">
        delete from student where id = #{id}
    </delete>

    <!--插入单个学生-->
    <insert id="insertOne" parameterType="Student">
        insert into student values(null,#{name},#{age},#{teacherId})
    </insert>

    <!--根据姓名查询学生，若姓名不为空，则用使用姓名查询，若为空，则查询全部-->
    <!--考察动态sql中的<if>使用-->
    <select id="selectByName" parameterType="Student" resultType="Student">
        <include refid="selectCommon"/>
        <!--如果是where子句的记得包含在where中-->
        <where>
            <if test="name!=null"><!--这面用test来进行判断-->
                and name = #{name}
            </if>
        </where>
    </select>

    <!--parameterType传参直接传具体的就可以了-->
    <select id="selectByIds" parameterType="java.util.List" resultType="Student">
        <include refid="selectCommon"/>
        <where>
            id in
            <!--想用下标就用下标即可-->
            <foreach collection="ids" item="id" open="(" close=")" index="index" separator=",">
                #{id}
            </foreach>
        </where>
    </select>

    <!--一对一查询-->
    <!--association针对一对一的场景-->
    <resultMap id="oneToOne" type="Person">
        <id column="pid" property="id"/>
        <result column="pname" property="name"/>
        <association property="card" javaType="Card">
            <id column="cid" property="id"/>
            <result column="ccno" property="cardNo"/>
        </association>
    </resultMap>

    <select id="selectPersonById" parameterType="int" resultMap="oneToOne">
        select p.id pid,p.name pname,c.id cid,c.card_no ccno from person p,card c where p.cid = c.id and p.id = #{id};
    </select>

    <!--为什么会有resultMap的配置，因为多表查询字段的问题-->
    <!--尝试一下一对多-->
    <!--这里的type也是最终生成的对象保存到哪里-->
    <resultMap id="oneToMany" type="Teacher">
        <!--column就是映射的列，要与select查询出的字段对应-->
        <!--type就是想映射到type中的字段名-->
        <!--除了id字段用id，其余用result-->
        <id column="tid" property="id"/>
        <result column="tname" property="name"/>
        <!--切记，返回一个列表时，一定不是List类型-->
        <collection property="students" ofType="Student">
            <id column="sid" property="id"/>
            <result column="sname" property="name"/>
            <result column="sage" property="age"/>
            <result column="stid" property="teacherId"/>
        </collection>
    </resultMap>

    <!--一对多查询-->
    <select id="selectTeacherById" parameterType="int" resultMap="oneToMany">
        select t.id tid,t.name tname,s.id sid,s.name sname,s.age sage,s.teacher_id stid
        from teacher t,student s
        where t.id = s.teacher_id and t.id = #{id};
    </select>


</mapper>
```

## 2.相关原理

1.接口代理方式实现Dao

在以往使用JDBC时，需要实现Mapper中的方法，接着获取数据库连接，执行对应的sql语句等等。

在mybatis中需要编写sql文件，但不需要实现Mapper的对应类，此类由mybatis框架根据创建接口以动态代理的方式动态生成。

由于是动态生成，所以要求mapper编写时需要遵循一些规范：

```
1）Mapper.xml文件中的namespace与mapper接口的全限定名相同
2）Mapper接口方法名和Mapper.xml中定义的每个statement(select update...)的id相同
3）Mapper接口方法的输入参数类型和Mapper.xml中定义的每个sql的parameterType的类型相同
4）Mapper接口方法的输出参数类型和Mapper.xml中定义的每个sql的resultType/resultMap的类型要相同
```

2.PageHelper分页插件

