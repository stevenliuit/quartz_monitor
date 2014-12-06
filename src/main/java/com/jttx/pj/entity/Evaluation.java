package com.jttx.pj.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name = "pj_evaluation")
public class Evaluation { //评价
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;//评价人姓名
    @Column(name="contract_num")
    private String contractNum;//合同编号
    @Column(name="agreement_score")
    private int agreementScore;//履约分数
    @Column(name="agreement_gist",columnDefinition = "TEXT")
    private String agreementGist;//履约打分依据
    @Column(name="att_score")
    private int serviceAttScore;//服务态度打分
    @Column(name="att_gist",columnDefinition = "TEXT")
    private String serviceAttGist;//服务态度打分依据
    @Column(name="material_score")
    private int materialScore;//用材打分
    @Column(name="material_gist",columnDefinition = "TEXT")
    private String materialGist;//用材打分依据
    @Column(name="quality_score")
    private int qualityScore;
    @Column(name="quality_gist",columnDefinition = "TEXT")
    private String qualityGist;//质量打分依据
    @Column(name="monitor_score")
    private int jobMonitorScore;//工程把控分数
    @Column(name="monitor_gist",columnDefinition = "TEXT")
    private String jobMonitorGist;//工程监控打分依据

    @Column(columnDefinition = "TEXT")
    private String employeeEval; //承建人评价
    @Column(columnDefinition = "TEXT")
    private String entEval;//企业评价
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;//评价时间

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "eval_id",nullable = false)
    private Set<Comment> comments=new HashSet<>();


    @ManyToOne(targetEntity=Enterprise.class)
    @JoinColumn(name="ent_id")
    private Enterprise enterprise;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name="user_id")
    private User user;


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

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public int getAgreementScore() {
        return agreementScore;
    }

    public void setAgreementScore(int agreementScore) {
        this.agreementScore = agreementScore;
    }

    public String getAgreementGist() {
        return agreementGist;
    }

    public void setAgreementGist(String agreementGist) {
        this.agreementGist = agreementGist;
    }

    public int getServiceAttScore() {
        return serviceAttScore;
    }

    public void setServiceAttScore(int serviceAttScore) {
        this.serviceAttScore = serviceAttScore;
    }

    public String getServiceAttGist() {
        return serviceAttGist;
    }

    public void setServiceAttGist(String serviceAttGist) {
        this.serviceAttGist = serviceAttGist;
    }

    public int getMaterialScore() {
        return materialScore;
    }

    public void setMaterialScore(int materialScore) {
        this.materialScore = materialScore;
    }

    public String getMaterialGist() {
        return materialGist;
    }

    public void setMaterialGist(String materialGist) {
        this.materialGist = materialGist;
    }

    public int getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(int qualityScore) {
        this.qualityScore = qualityScore;
    }

    public String getQualityGist() {
        return qualityGist;
    }

    public void setQualityGist(String qualityGist) {
        this.qualityGist = qualityGist;
    }

    public int getJobMonitorScore() {
        return jobMonitorScore;
    }

    public void setJobMonitorScore(int jobMonitorScore) {
        this.jobMonitorScore = jobMonitorScore;
    }

    public String getJobMonitorGist() {
        return jobMonitorGist;
    }

    public void setJobMonitorGist(String jobMonitorGist) {
        this.jobMonitorGist = jobMonitorGist;
    }

    public String getEmployeeEval() {
        return employeeEval;
    }

    public void setEmployeeEval(String employeeEval) {
        this.employeeEval = employeeEval;
    }

    public String getEntEval() {
        return entEval;
    }

    public void setEntEval(String entEval) {
        this.entEval = entEval;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
