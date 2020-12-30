package com.pmdgjjw.efguser.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @auth jian j w
 * @date 2020/8/22 21:25
 * @Description
 */
@Table(name = "sys_user")
public class WKUser {

    @Id
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "head_portrait")
    private String headPortrait;

    @Transient
    private String nickName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "WKUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
