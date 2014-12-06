package com.jttx.pj.dao;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by louis on 2014/12/6.
 */
public abstract class AbstractBaseDao<T> {
    public abstract T save(T t);

    public abstract T saveOrUpdate(T t);

    public abstract T persist(T t);

    public abstract T get(Class<T> clazz, Long id);

    public abstract void delete(T t);

    public abstract void update(T t);

    public abstract T load(Class<T> clazz, Long id);

    public abstract List<T> list(Class<T> clazz, int pageNum, int pageSize);

    public abstract List<T> list(String hql, Object[] params, int pageNum, int pageSize);

    public abstract Long count(String hql, Object[] params);

    public abstract Long count(Class<T> clazz);

    public abstract void flush();
}
