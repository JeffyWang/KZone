package com.kzone.dao.impl;

import com.kzone.bean.User;
import com.kzone.dao.InitializeDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Jeffy on 2014/6/19 0019.
 */
@Transactional
public class InitializeDaoImpl extends HibernateDaoSupport implements InitializeDao {

    @Resource(name = "sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void executeHQL(String hql) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        try{
            session.createSQLQuery(hql).executeUpdate();
        } catch (Exception e) {

        } finally {
            session.close();
        }
    }
}
