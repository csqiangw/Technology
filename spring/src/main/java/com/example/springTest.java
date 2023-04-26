package com.example;

import com.example.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class springTest {

    @Test
    public void testBeanXml(){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        //在AbstractApplicationContext中实现了容器的关闭方法
        AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        //一种是通过<import/>的方式在一个主文件引入，也可以通过传参的方式加载
        //并且先定义的覆盖后定义的
//        AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml","spring-context-others.xml");
        UserService us = (UserService)applicationContext.getBean("us");
        us.travel();
        applicationContext.close();
    }

}
