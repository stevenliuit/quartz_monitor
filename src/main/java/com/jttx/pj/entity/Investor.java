package com.jttx.pj.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name="pj_investor")
public class Investor { //出资人
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String idNum;//身份证
    private String loanId;//贷款卡编码
    @Column(name="cert_type")
    private String certType;//证件类型
    @Column(name="org_code")
    private String orgCode;//组织机构代码
    @Column(name="invest_type")
    private String investType;//出资方式
    @Column(name="reg_num")
    private String regNum;//等级注册号
    @Column(name="invest_mount")
    private double investMount;//出资金额
    @Column(name="eval_org")
    private String evalOrgName;//资产评估机构名称
    @Column(name="invest_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date investDate;


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

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public double getInvestMount() {
        return investMount;
    }

    public void setInvestMount(double investMount) {
        this.investMount = investMount;
    }

    public String getEvalOrgName() {
        return evalOrgName;
    }

    public void setEvalOrgName(String evalOrgName) {
        this.evalOrgName = evalOrgName;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }
}
