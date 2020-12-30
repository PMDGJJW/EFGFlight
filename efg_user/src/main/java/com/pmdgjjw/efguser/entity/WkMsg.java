package com.pmdgjjw.efguser.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/8/25 13:16
 * @Description
 */
public class WkMsg implements Serializable {

    private String uid;

    private String msg;


    private Date date;


    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WkMsg{" +
                "uid='" + uid + '\'' +
                ", msg='" + msg + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
