package org.beatengine.onlineshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.math.BigInteger;


public class UserRole extends SuperEntity{
    @Id
    //@Column(columnDefinition = "BIGINT")
    private Long userId;
    @Id
    //@Column(columnDefinition = "BIGINT")
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
