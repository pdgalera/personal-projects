package com.springjpatransactional.db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pablo on 30/08/18.
 */

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class AbstractDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Class<T> persistentClass;
    
    public AbstractDAO() {
    }

    public void setPersistentClass(Class<T> clazz) {
        this.persistentClass = clazz;
    }
    
    public T save(T object) {
        return (T) sessionFactory.getCurrentSession().merge(object);
    }

    public List<T> findAll() {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(persistentClass);
        return crit.list();
    }

    public T find(Serializable id) {
        return (T) sessionFactory.getCurrentSession().get(persistentClass, id);
    }

    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}