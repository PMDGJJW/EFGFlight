package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "newInfoDetail")
public class NewInfoDetail {

    @Id
    private Integer id;

    private Integer commentId;

    private String commentTitle;

    private String msgInfo;

    private Long userId;

    private String userName;

    private String userHead;

    private Integer isRead;

    private String spitId;

    public String getSpitId() {
        return spitId;
    }

    public void setSpitId(String spitId) {
        this.spitId = spitId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public NewInfoDetail() {
    }

    public NewInfoDetail(Integer id, Integer commentId, String commentTitle, String msgInfo, Long userId, String userName, String userHead, Integer isRead, String spitId, Date createTime) {
        this.id = id;
        this.commentId = commentId;
        this.commentTitle = commentTitle;
        this.msgInfo = msgInfo;
        this.userId = userId;
        this.userName = userName;
        this.userHead = userHead;
        this.isRead = isRead;
        this.spitId = spitId;
        this.createTime = createTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
