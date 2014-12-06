package com.jttx.pj.service;

import com.jttx.pj.dao.UserDao;
import com.jttx.pj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yamorn on 2014/12/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void save(User user) {
        userDao.saveUser(user);
    }
}
