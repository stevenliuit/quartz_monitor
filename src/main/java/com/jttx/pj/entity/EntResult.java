package com.jttx.pj.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name="pj_ent_result")
public class EntResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="BLOB")
    private byte[] image;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
