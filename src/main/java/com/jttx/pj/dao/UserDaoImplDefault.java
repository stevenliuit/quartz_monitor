package com.jttx.pj.dao;

import com.jttx.pj.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by yamorn on 2014/12/7.
 */
@Repository
public class UserDaoImplDefault extends DefaultBaseDao<User> implements UserDao {
    @Override
    public void saveUser(User user) {
        save(user);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }
}
