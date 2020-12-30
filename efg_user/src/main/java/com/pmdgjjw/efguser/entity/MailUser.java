package com.pmdgjjw.efguser.entity;

/**
 * @auth jian j w
 * @date 2020/7/27 12:41
 * @Description
 */
public class MailUser {


    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MailUser() {
    }

    public MailUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "MailUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
