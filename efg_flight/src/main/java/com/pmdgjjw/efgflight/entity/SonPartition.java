package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "son_partition")
public class SonPartition implements Serializable {
    /**
     * 子分区ID
     */
    @Id
    private Integer id;

    private Integer hotCount;

    /**
     * 子分区名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateDate;

    /**
     * 主题数
     */
    private Integer topics;

    /**
     * 回复数
     */
    private Integer comments;

    /**
     * 最新发帖创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 最新发帖标题
     */
    @Column(name = "new_comment")
    private String newComment;

    /**
     * 是否删除 1是0否
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    @Transient
    private String newReplyTime;
    @Transient
    private String newCreateTime;

    @Column(name = "parent_id")
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getNewCreateTime() {
        return newCreateTime;
    }

    public void setNewCreateTime(String newCreateTime) {
        this.newCreateTime = newCreateTime;
    }

    public String getNewReplyTime() {
        return newReplyTime;
    }

    public void setNewReplyTime(String newReplyTime) {
        this.newReplyTime = newReplyTime;
    }

    /**
     * 获取子分区ID
     *
     * @return id - 子分区ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置子分区ID
     *
     * @param id 子分区ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取子分区名称
     *
     * @return name - 子分区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置子分区名称
     *
     * @param name 子分区名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取主题数
     *
     * @return topics - 主题数
     */
    public Integer getTopics() {
        return topics;
    }

    /**
     * 设置主题数
     *
     * @param topics 主题数
     */
    public void setTopics(Integer topics) {
        this.topics = topics;
    }

    /**
     * 获取回复数
     *
     * @return comments - 回复数
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * 设置回复数
     *
     * @param comments 回复数
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * 获取最新发帖创建人
     *
     * @return create_by - 最新发帖创建人
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置最新发帖创建人
     *
     * @param createBy 最新发帖创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取最新发帖标题
     *
     * @return new_comment - 最新发帖标题
     */
    public String getNewComment() {
        return newComment;
    }

    /**
     * 设置最新发帖标题
     *
     * @param newComment 最新发帖标题
     */
    public void setNewComment(String newComment) {
        this.newComment = newComment == null ? null : newComment.trim();
    }

    /**
     * 获取是否删除 1是0否
     *
     * @return del_flag - 是否删除 1是0否
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除 1是0否
     *
     * @param delFlag 是否删除 1是0否
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getHotCount() {
        return hotCount;
    }

    public void setHotCount(Integer hotCount) {
        this.hotCount = hotCount;
    }

    @Override
    public String toString() {
        return "SonPartition{" +
                "id=" + id +
                ", hotCount=" + hotCount +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", topics=" + topics +
                ", comments=" + comments +
                ", createBy=" + createBy +
                ", newComment='" + newComment + '\'' +
                ", delFlag=" + delFlag +
                ", newReplyTime='" + newReplyTime + '\'' +
                ", newCreateTime='" + newCreateTime + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
