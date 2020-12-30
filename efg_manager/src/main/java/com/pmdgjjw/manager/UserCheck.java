package com.pmdgjjw.manager;

import java.util.Objects;

/**
 * @auth jian j w
 * @date 2020/8/9 21:15
 * @Description
 */
public class UserCheck  {


    private Long id;

    private String name;

    private Integer userType;

    public UserCheck() {
    }

    public UserCheck(Long id, String name, Integer userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserCheck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userType=" + userType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCheck userCheck = (UserCheck) o;
        return Objects.equals(id, userCheck.id) &&
                Objects.equals(name, userCheck.name) &&
                Objects.equals(userType, userCheck.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userType);
    }
}
