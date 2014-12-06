package com.jttx.pj.entity;

/**
 * Created by louis on 2014/12/6.
 */
public enum  RoleType {
    ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE,ROLE_ENTERPRISE;
    @Override
    public String toString() {
        String str="ROLE_CUSTOMER";
        switch (this){
            case ROLE_ADMIN:
                str = "ROLE_ADMIN";
                break;
            case ROLE_CUSTOMER:
                str = "ROLE_CUSTOMER";
                break;
            case ROLE_EMPLOYEE:
                str = "ROLE_EMPLOYEE";
                break;
            case ROLE_ENTERPRISE:
                str = "ROLE_ENTERPRISE";
                break;
        }
        return str;
    }


}
