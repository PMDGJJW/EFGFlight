package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/7/23 19:06
 * @Description
 */
@Table(name = "comments")
public class BaseComment implements Serializable {

    @Id
    private Integer id;

    private String title;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "user_name")
    private String userName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 回复时间
     */
    @Column(name = "comment_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date commentTime;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "see_count")
    private Integer seeCount;

    @Column(name = "categort_name")
    private String categortName;

    @Transient
    private String NewCommentTime;

    @Transient
    private String NewCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    public String getCategortName() {
        return categortName;
    }

    public void setCategortName(String categortName) {
        this.categortName = categortName;
    }

    public String getNewCommentTime() {
        return NewCommentTime;
    }

    public void setNewCommentTime(String newCommentTime) {
        NewCommentTime = newCommentTime;
    }

    public String getNewCreateTime() {
        return NewCreateTime;
    }

    public void setNewCreateTime(String newCreateTime) {
        NewCreateTime = newCreateTime;
    }

    @Override
    public String toString() {
        return "BaseComment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", uid=" + uid +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", commentTime=" + commentTime +
                ", replyCount=" + replyCount +
                ", seeCount=" + seeCount +
                ", categortName='" + categortName + '\'' +
                ", NewCommentTime='" + NewCommentTime + '\'' +
                ", NewCreateTime='" + NewCreateTime + '\'' +
                '}';
    }
}
