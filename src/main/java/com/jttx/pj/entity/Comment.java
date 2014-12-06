package com.jttx.pj.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name = "pj_comment")
public class Comment { //评价注释
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
