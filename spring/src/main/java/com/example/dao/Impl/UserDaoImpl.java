package com.example.dao.Impl;

import com.example.dao.UserDao;

public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
    }

    @Override
    public void insertUser() {
        System.out.println("向数据库插入一个用户");
    }

}
