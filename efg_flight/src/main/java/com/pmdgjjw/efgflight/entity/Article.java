package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @auth jian j w
 * @date 2020/8/16 1:06
 * @Description
 */
@Document(indexName="efgflight",type = "comment")
public class Article implements Serializable {

    @Id
    private String id;

    private Integer cid;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String comment;

    @JsonProperty("see_count")
    private Integer seeCount;

    @JsonProperty("reply_count")
    private Integer replyCount;

    @JsonProperty("create_time")
//    @DateTimeFormat(pattern = "yyyy-MM-dd ")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer suid;

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("parent_id")
    private Integer parentId;

    private String spname;

    public Article() {
    }

    public Article(String id, Integer cid, String title, String comment, Integer seeCount, Integer replyCount, Date createTime, Integer suid, String userName, Integer parentId, String spname) {
        this.id = id;
        this.cid = cid;
        this.title = title;
        this.comment = comment;
        this.seeCount = seeCount;
        this.replyCount = replyCount;
        this.createTime = createTime;
        this.suid = suid;
        this.userName = userName;
        this.parentId = parentId;
        this.spname = spname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", cid=" + cid +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", seeCount=" + seeCount +
                ", replyCount=" + replyCount +
                ", createTime=" + createTime +
                ", suid=" + suid +
                ", userName='" + userName + '\'' +
                ", parentId=" + parentId +
                ", spname='" + spname + '\'' +
                '}';
    }
}
