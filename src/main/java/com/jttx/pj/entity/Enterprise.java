package com.jttx.pj.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name = "pj_enterprise")
public class Enterprise implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique=true)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(name="org_code")
    private String orgCode;
    @Column(name="reg_num")
    private String regNum;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String honor;
    @Column(name="reg_capital")
    private double registCapital;//注册资本
    @Column(name="real_paid")
    private double readPaid;

    @OneToMany(mappedBy = "enterprise",cascade = CascadeType.ALL)
    private List<User> users=new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ent_id",nullable = false)
    private List<EntResult> entResults=new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ent_id",nullable = false)
    private List<Investor> investors=new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ent_id",nullable = false)
    private List<Contract> contracts=new LinkedList<>();

    public void addUser(User user){
        if(user==null) {
            throw new RuntimeException("User can't be null");
        }
        this.users.add(user);
        user.setEnterprise(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public double getRegistCapital() {
        return registCapital;
    }

    public void setRegistCapital(double registCapital) {
        this.registCapital = registCapital;
    }

    public double getReadPaid() {
        return readPaid;
    }

    public void setReadPaid(double readPaid) {
        this.readPaid = readPaid;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<EntResult> getEntResults() {
        return entResults;
    }

    public void setEntResults(List<EntResult> entResults) {
        this.entResults = entResults;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public void setInvestors(List<Investor> investors) {
        this.investors = investors;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
