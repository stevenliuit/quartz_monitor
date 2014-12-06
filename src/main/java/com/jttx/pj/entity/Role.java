package com.jttx.pj.entity;

import javax.persistence.*;

/**
 * Created by louis on 2014/12/6.
 */
@Entity
@Table(name = "pj_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    public Role(){}

    public Role(RoleType roleType) {
        role=roleType.toString();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
