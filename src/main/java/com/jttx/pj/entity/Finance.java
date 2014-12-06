package com.jttx.pj.entity;

import javax.persistence.*;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name="pj_finance")
public class Finance { //企业财务状况
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="filename")
    private String fileName;
    @Column(columnDefinition="BLOB")
    private byte[] file;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
