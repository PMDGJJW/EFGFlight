package com.pmdgjjw.efgflight.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auth jian j w
 * @date 2020/7/7 10:08
 * @Description
 */
@Table(name = "gold")
public class Gold {

    @Id
    private Integer id;

    @Column(name = "gold_size")
    private Integer gsize;

    @Column(name = "gold_count")
    private Integer gcount;

    @Column(name = "cid")
    private Integer cid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGsize() {
        return gsize;
    }

    public void setGsize(Integer gsize) {
        this.gsize = gsize;
    }

    public Integer getGcount() {
        return gcount;
    }

    public void setGcount(Integer gcount) {
        this.gcount = gcount;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Gold{" +
                "id=" + id +
                ", gsize=" + gsize +
                ", gcount=" + gcount +
                ", cid=" + cid +
                '}';
    }
}

