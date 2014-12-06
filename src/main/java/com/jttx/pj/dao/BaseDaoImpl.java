package com.jttx.pj.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by louis on 2014/12/6.
 */

@Repository
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDAO<T> {
    @Resource
    protected SessionFactory sessionFactory;

    public T save(T t) {
        
        sessionFactory.getCurrentSession().save(t);
        return t;
    }

    @Override
    public T saveOrUpdate(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);
        return t;
    }

    @Override
    public T persist(T t) {
        sessionFactory.getCurrentSession().persist(t);
        return t;
    }

    @Override
    public T get(Class<T> clazz, Long id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    public void delete(T t) {
        sessionFactory.getCurrentSession().delete(t);
    }

    public void update(T t) {
        sessionFactory.getCurrentSession().update(t);
    }

    public T load(Class<T> clazz, Long id) {
        return (T) sessionFactory.getCurrentSession().load(clazz, id);
    }

    public Long count(Class<T> clazz) {
        String hql = String.format("select count(p) from %s as p", clazz.getSimpleName());
        return (Long) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
    }

    @Override
    public List<T> list(Class<T> clazz, int pageNum, int pageSize) {
        String hql = String.format("select t from %s as t", clazz.getSimpleName());
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult(pageNum - 1);
        query.setMaxResults(pageSize);
        return (List<T>) query.list();
    }

    @Override
    public List<T> list(String hql, Object[] params, int pageNum, int pageSize) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        query.setFirstResult(pageNum - 1);
        query.setMaxResults(pageSize);
        return (List<T>) query.list();
    }

    @Override
    public Long count(String hql, Object[] params) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        return (Long) query.uniqueResult();
    }

    @Override
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }
}
