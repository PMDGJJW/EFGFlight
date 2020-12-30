package com.pmdgjjw.efguser.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/8/29 0:35
 * @Description
 */
public class WxMsg implements Serializable {

    private Long cid;

    private Long uid;

    private Long upid;

    private String msg;

    private String date;

    private Date sendDate;

    private String cTitle;

    private String uName;

    private String headp;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getHeadp() {
        return headp;
    }

    public void setHeadp(String headp) {
        this.headp = headp;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUpid() {
        return upid;
    }

    public void setUpid(Long upid) {
        this.upid = upid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    @Override
    public String toString() {
        return "WxMsg{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", upid=" + upid +
                ", msg='" + msg + '\'' +
                ", date='" + date + '\'' +
                ", sendDate=" + sendDate +
                ", cTitle='" + cTitle + '\'' +
                ", uName='" + uName + '\'' +
                ", headp='" + headp + '\'' +
                '}';
    }
}
