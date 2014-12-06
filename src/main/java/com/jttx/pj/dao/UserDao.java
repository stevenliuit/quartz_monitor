package com.jttx.pj.dao;

import com.jttx.pj.entity.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yamorn on 2014/12/7.
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
public interface UserDao{
    public void saveUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);

}
