package com.pmdgjjw.efguser.entity;

import java.io.Serializable;

/**
 * @auth jian j w
 * @date 2020/8/29 0:48
 * @Description
 */
public class WxReplyMsg implements Serializable {

    private Long cid;

    private Long uid;

    private String msg;

    private String news;

    private String uName;

    private String headp;

    private String cTitle;

    private String data;

    private Long userid;

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "WxReplyMsg{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", msg='" + msg + '\'' +
                ", news='" + news + '\'' +
                ", uName='" + uName + '\'' +
                ", headp='" + headp + '\'' +
                ", cTitle='" + cTitle + '\'' +
                ", data='" + data + '\'' +
                ", userid=" + userid +
                '}';
    }
}
