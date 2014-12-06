package com.jttx.pj.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {
    @Autowired
    private SessionFactory sessionFactory;

    private Session session;
    @Before
    public void init(){
        session=sessionFactory.getCurrentSession();
    }
    @Test
    @Ignore
    @Rollback(false)
    public void saveUser(){
        User user=new User();
        user.setIdNum("234");
        user.setUsername("tom");
        user.setPassword("23");
        user.setDate(new Date());
        user.setEmail("test@outlook.com");
        session.save(user);
    }
    @Test
    @Ignore
    @Rollback(false)
    public void updateUser() {
        User user=(User)session.load(User.class, new Long(2));
        assertNotNull(user);
        user.setUsername("Jack");
        session.update(user);
    }

    @Test
    @Rollback(false)
    public void saveUserWithRolse(){
        User user=new User();
        user.setUsername("tom");
        user.setPassword("123");
        user.setEmail("tom@hotmail.com");
        user.setDate(new Date());
        user.setPhone("10086");
        user.setGender("body");
        user.setAccount("tom");
        Role role=new Role(RoleType.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoleSet(roles);
        session.save(user);
    }
    @Test
    @Ignore
    @Rollback(false)
    public void saveUserWithAptitude(){
        User user=new User();
        user.setUsername("jack");
        user.setPassword("1233");
        user.setEmail("jack@hotmail.com");
        user.setDate(new Date());
        user.setPhone("123");
        user.setGender("body");

        Aptitude aptitude1=new Aptitude();
        aptitude1.setName("honor_1");
        Aptitude aptitude2=new Aptitude();
        aptitude2.setName("honor_2");
        Set<Aptitude> aptitudeSet = new HashSet<>();
        aptitudeSet.add(aptitude1);
        aptitudeSet.add(aptitude2);
        user.setAptitudes(aptitudeSet);
        session.save(user);
    }
    @Test
    @Ignore
    @Rollback(false)
    public void saveEnterpriseWithUser(){
        User user=new User();
        user.setUsername("jack");
        user.setPassword("1233");
        user.setEmail("jack@hotmail.com");
        user.setDate(new Date());
        user.setPhone("123");
        user.setGender("body");

        Aptitude aptitude1=new Aptitude();
        aptitude1.setName("honor_1");
        Aptitude aptitude2=new Aptitude();
        aptitude2.setName("honor_2");
        Set<Aptitude> aptitudeSet = new HashSet<>();
        aptitudeSet.add(aptitude1);
        aptitudeSet.add(aptitude2);
        user.setAptitudes(aptitudeSet);

        Enterprise enterprise=new Enterprise();
        enterprise.setName("jttx");
        enterprise.setDate(new Date());
        enterprise.setAddress("shanghai");
        enterprise.setDescription("hhh");
        enterprise.setOrgCode("33");
        enterprise.setRegNum("123");
        Set<User> users = new HashSet<>();
        users.add(user);
        user.setEnterprise(enterprise);
        enterprise.setUsers(users);
        session.save(enterprise);
    }
    @Test
    @Ignore
    public void listUser() {
        Enterprise enterprise = (Enterprise) session.load(Enterprise.class, new Long(1));
        assertNotNull(enterprise);
        Set<User> users = enterprise.getUsers();
        assertTrue(users.size() > 0);
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }
}