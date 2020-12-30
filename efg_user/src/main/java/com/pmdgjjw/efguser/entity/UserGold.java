package com.pmdgjjw.efguser.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auth jian j w
 * @date 2020/7/8 9:48
 * @Description
 */
@Table(name = "user_gold")
public class UserGold {

    @Id
    private Integer id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "gprice")
    private Integer gprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getGprice() {
        return gprice;
    }

    public void setGprice(Integer gprice) {
        this.gprice = gprice;
    }

    @Override
    public String toString() {
        return "UserGold{" +
                "id=" + id +
                ", uid=" + uid +
                ", gprice=" + gprice +
                '}';
    }
}
