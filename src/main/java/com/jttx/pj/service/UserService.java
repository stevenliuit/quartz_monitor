package com.jttx.pj.service;

import com.jttx.pj.entity.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yamorn on 2014/12/7.
 */
public interface UserService {
    public void save(User user);
}
