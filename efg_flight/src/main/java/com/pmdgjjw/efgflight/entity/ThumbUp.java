package com.pmdgjjw.efgflight.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auth jian j w
 * @date 2020/7/12 23:37
 * @Description
 */
@Table(name = "thumbup")
public class ThumbUp {

    @Id
    private Integer id;

    @Column(name = "sid")
    private String sid;

    @Column(name = "uid")
    private String uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "ThumbUp{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
