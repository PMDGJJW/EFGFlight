package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/2 9:42
 * @Description
 */
@Document( "test")
public class Spit implements Serializable {

    @Id
    private String id;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishtime;

    @Transient
    private String newPublishTime;

   @Transient
    private String TrTime;
    private String userid;
    private String nickname;

    private String cateId;

    private Integer visits;
    private Integer thumbup;
    private Integer share;
    private Integer thumbdw;
    private String state;
    private List<Pspit> pspit;

    private String parentid;//父 id

    private Integer partitionId;

    private Integer sonPartitionId;

    private  Integer gCheck;

    @Transient
    private Integer LikeCheck;
    @Transient
    private Integer DwCheck;

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public Integer getSonPartitionId() {
        return sonPartitionId;
    }

    public void setSonPartitionId(Integer sonPartitionId) {
        this.sonPartitionId = sonPartitionId;
    }

    public Integer getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Integer partitionId) {
        this.partitionId = partitionId;
    }

    public Integer getLikeCheck() {
        return LikeCheck;
    }

    public void setLikeCheck(Integer likeCheck) {
        LikeCheck = likeCheck;
    }

    public Integer getDwCheck() {
        return DwCheck;
    }

    public void setDwCheck(Integer dwCheck) {
        DwCheck = dwCheck;
    }

    public Integer getgCheck() {
        return gCheck;
    }

    public void setgCheck(Integer gCheck) {
        this.gCheck = gCheck;
    }

    public String getNewPublishTime() {
        return newPublishTime;
    }

    public void setNewPublishTime(String newPublishTime) {
        this.newPublishTime = newPublishTime;
    }

    public String getTrTime() {
        return TrTime;
    }

    public void setTrTime(String trTime) {
        TrTime = trTime;
    }

    public List<Pspit> getPspit() {
        return pspit;
    }

    public void setPspit(List<Pspit> pspit) {
        this.pspit = pspit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getThumbup() {
        return thumbup;
    }

    public void setThumbup(Integer thumbup) {
        this.thumbup = thumbup;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getThumbdw() {
        return thumbdw;
    }

    public void setThumbdw(Integer thumbdw) {
        this.thumbdw = thumbdw;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "Spit{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", publishtime=" + publishtime +
                ", newPublishTime='" + newPublishTime + '\'' +
                ", TrTime='" + TrTime + '\'' +
                ", userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", visits=" + visits +
                ", thumbup=" + thumbup +
                ", share=" + share +
                ", thumbdw=" + thumbdw +
                ", state='" + state + '\'' +
                ", pspit=" + pspit +
                ", parentid='" + parentid + '\'' +
                ", gCheck=" + gCheck +
                ", LikeCheck=" + LikeCheck +
                ", DwCheck=" + DwCheck +
                '}';
    }
}
