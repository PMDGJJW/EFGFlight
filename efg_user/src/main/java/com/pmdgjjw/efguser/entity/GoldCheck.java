package com.pmdgjjw.efguser.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auth jian j w
 * @date 2020/7/12 9:56
 * @Description
 */
@Table(name = "gold_check")
public class GoldCheck {

    @Id
    private Integer id;

    @Column(name = "cid")
    private Integer cid;
    @Column(name = "uid")
    private Long uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "GoldCheck{" +
                "id=" + id +
                ", cid=" + cid +
                ", uid=" + uid +
                '}';
    }
}
