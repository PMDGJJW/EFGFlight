package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "comments")
public class Comments implements Serializable {
    /**
     * 帖子ID
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子类别
     */
    private Integer categort;

    /**
     * 是否是热门 1是0否
     */
    private Integer ishot;

    /**
     * 是否是精品 1是0否
     */
    private Integer issurper;

    /**
     * 是否已经审核 1是0否
     */
    private Integer isapprove;

    /**
     * 奖励类别
     */
    private Integer reward;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

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

    @Column(name = "newreply_id")
    private Long newreplyId;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Transient
    private String categortName;

    @Transient
    private String categortId;

    private String comment;

    private SysUser sysUser;

    @Transient
    private String replyName;
    @Transient
    private String createName;

    @Transient
    private Integer isNewComment;

    @Transient
    private String NewCreateTime;

    @Transient
    private  String NewUpdateTime;

    @Transient
    private Integer isNewUserComment;

    @Transient
    private String NewCommentTime;

    @Transient
    private String cateName;

    @Transient
    private List<Spit> spits;

    @Transient
    private MgPage mgPage;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "is_band")
    private Integer isBand;

    @Column(name = "un_save")
    private Integer unSave;

    public String getCategortName() {
        return categortName;
    }

    public void setCategortName(String categortName) {
        this.categortName = categortName;
    }

    public String getCategortId() {
        return categortId;
    }

    public void setCategortId(String categortId) {
        this.categortId = categortId;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getIsNewComment() {
        return isNewComment;
    }

    public void setIsNewComment(Integer isNewComment) {
        this.isNewComment = isNewComment;
    }

    public String getNewCreateTime() {
        return NewCreateTime;
    }

    public void setNewCreateTime(String newCreateTime) {
        NewCreateTime = newCreateTime;
    }

    public String getNewUpdateTime() {
        return NewUpdateTime;
    }

    public void setNewUpdateTime(String newUpdateTime) {
        NewUpdateTime = newUpdateTime;
    }

    public Integer getIsNewUserComment() {
        return isNewUserComment;
    }

    public void setIsNewUserComment(Integer isNewUserComment) {
        this.isNewUserComment = isNewUserComment;
    }

    public String getNewCommentTime() {
        return NewCommentTime;
    }

    public void setNewCommentTime(String newCommentTime) {
        NewCommentTime = newCommentTime;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Spit> getSpits() {
        return spits;
    }

    public void setSpits(List<Spit> spits) {
        this.spits = spits;
    }

    public MgPage getMgPage() {
        return mgPage;
    }

    public void setMgPage(MgPage mgPage) {
        this.mgPage = mgPage;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsBand() {
        return isBand;
    }

    public void setIsBand(Integer isBand) {
        this.isBand = isBand;
    }

    public Integer getUnSave() {
        return unSave;
    }

    public void setUnSave(Integer unSave) {
        this.unSave = unSave;
    }

    public Comments() {
    }

    public Comments(String title, Integer categort, Integer ishot, Integer issurper, Integer isapprove, Integer reward, Date createTime, Date updateTime, Date commentTime, Integer replyCount, Integer seeCount, Long newreplyId, Integer delFlag, String categortName, String categortId, String comment, SysUser sysUser, String replyName, String createName, Integer isNewComment, String newCreateTime, String newUpdateTime, Integer isNewUserComment, String newCommentTime, String cateName, List<Spit> spits, MgPage mgPage, Long createBy, int parentId, int isBand, int unSave) {
        this.title = title;
        this.categort = categort;
        this.ishot = ishot;
        this.issurper = issurper;
        this.isapprove = isapprove;
        this.reward = reward;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.commentTime = commentTime;
        this.replyCount = replyCount;
        this.seeCount = seeCount;
        this.newreplyId = newreplyId;
        this.delFlag = delFlag;
        this.categortName = categortName;
        this.categortId = categortId;
        this.comment = comment;
        this.sysUser = sysUser;
        this.replyName = replyName;
        this.createName = createName;
        this.isNewComment = isNewComment;
        NewCreateTime = newCreateTime;
        NewUpdateTime = newUpdateTime;
        this.isNewUserComment = isNewUserComment;
        NewCommentTime = newCommentTime;
        this.cateName = cateName;
        this.spits = spits;
        this.mgPage = mgPage;
        this.createBy = createBy;
        this.parentId = parentId;
        this.isBand = isBand;
        this.unSave = unSave;
    }

    /**
     * 获取帖子ID
     *
     * @return id - 帖子ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置帖子ID
     *
     * @param id 帖子ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取帖子标题
     *
     * @return title - 帖子标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置帖子标题
     *
     * @param title 帖子标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取帖子类别
     *
     * @return categort - 帖子类别
     */
    public Integer getCategort() {
        return categort;
    }

    /**
     * 设置帖子类别
     *
     * @param categort 帖子类别
     */
    public void setCategort(Integer categort) {
        this.categort = categort;
    }

    /**
     * 获取是否是热门 1是0否
     *
     * @return ishot - 是否是热门 1是0否
     */
    public Integer getIshot() {
        return ishot;
    }

    /**
     * 设置是否是热门 1是0否
     *
     * @param ishot 是否是热门 1是0否
     */
    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    /**
     * 获取是否是精品 1是0否
     *
     * @return issurper - 是否是精品 1是0否
     */
    public Integer getIssurper() {
        return issurper;
    }

    /**
     * 设置是否是精品 1是0否
     *
     * @param issurper 是否是精品 1是0否
     */
    public void setIssurper(Integer issurper) {
        this.issurper = issurper;
    }

    /**
     * 获取是否已经审核 1是0否
     *
     * @return isapprove - 是否已经审核 1是0否
     */
    public Integer getIsapprove() {
        return isapprove;
    }

    /**
     * 设置是否已经审核 1是0否
     *
     * @param isapprove 是否已经审核 1是0否
     */
    public void setIsapprove(Integer isapprove) {
        this.isapprove = isapprove;
    }

    /**
     * 获取奖励类别
     *
     * @return reward - 奖励类别
     */
    public Integer getReward() {
        return reward;
    }

    /**
     * 设置奖励类别
     *
     * @param reward 奖励类别
     */
    public void setReward(Integer reward) {
        this.reward = reward;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取回复时间
     *
     * @return comment_time - 回复时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 设置回复时间
     *
     * @param commentTime 回复时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * @return reply_count
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * @param replyCount
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * @return see_count
     */
    public Integer getSeeCount() {
        return seeCount;
    }

    /**
     * @param seeCount
     */
    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    /**
     * @return newreply_id
     */
    public Long getNewreplyId() {
        return newreplyId;
    }

    /**
     * @param newreplyId
     */
    public void setNewreplyId(Long newreplyId) {
        this.newreplyId = newreplyId;
    }

    /**
     * @return del_flag
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return describe
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categort=" + categort +
                ", ishot=" + ishot +
                ", issurper=" + issurper +
                ", isapprove=" + isapprove +
                ", reward=" + reward +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", commentTime=" + commentTime +
                ", replyCount=" + replyCount +
                ", seeCount=" + seeCount +
                ", newreplyId=" + newreplyId +
                ", delFlag=" + delFlag +
                ", categortName='" + categortName + '\'' +
                ", categortId='" + categortId + '\'' +
                ", comment='" + comment + '\'' +
                ", sysUser=" + sysUser +
                ", replyName='" + replyName + '\'' +
                ", createName='" + createName + '\'' +
                ", isNewComment=" + isNewComment +
                ", NewCreateTime='" + NewCreateTime + '\'' +
                ", NewUpdateTime='" + NewUpdateTime + '\'' +
                ", isNewUserComment=" + isNewUserComment +
                ", NewCommentTime='" + NewCommentTime + '\'' +
                ", cateName='" + cateName + '\'' +
                ", spits=" + spits +
                ", mgPage=" + mgPage +
                '}';
    }
}
