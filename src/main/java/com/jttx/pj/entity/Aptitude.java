package com.jttx.pj.entity;

import javax.persistence.*;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name = "pj_aptitude")
public class Aptitude { //企业员工个人资质
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition="BLOB")
    private byte[] content;

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
