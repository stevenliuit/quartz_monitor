package com.jttx.pj.dao;


import java.util.List;

/**
 * Created by louis on 2014/12/6.
 */
public interface BaseDAO<T> {
    T save(T t);

    T saveOrUpdate(T t);

    T persist(T t);

    T get(Class<T> clazz, Long id);

    void delete(T t);

    void update(T t);

    T load(Class<T> clazz, Long id);

    List<T> list(Class<T> clazz, int pageNum, int pageSize);

    List<T> list(String hql, Object[] params, int pageNum, int pageSize);

    Long count(String hql, Object[] params);

    Long count(Class<T> clazz);

    void flush();
}
