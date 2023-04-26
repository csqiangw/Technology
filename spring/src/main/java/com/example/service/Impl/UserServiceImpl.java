package com.example.service.Impl;

import com.example.dao.UserDao;
import com.example.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    //必须提供get set方法
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl() {
    }

    //对象初始化时执行
    public void initMethod(){
        System.out.println("对象初始化");
    }

    @Override
    public void travel() {
        System.out.println("用户访问");
        userDao.insertUser();
    }

    //对象销毁时执行
    public void destoryMethod(){
        System.out.println("对象销毁");
    }

}
