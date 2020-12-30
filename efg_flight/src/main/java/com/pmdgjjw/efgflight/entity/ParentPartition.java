package com.pmdgjjw.efgflight.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "parent_partition")
public class ParentPartition  implements Serializable {
    /**
     * 分区ID
     */
    @Id
    private Integer id;

    /**
     * 分区名称
     */
    private String name;

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
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 是否删除 1是0否
     */
    @Column(name = "del_flag")
    private String delFlag;

    @Transient
    private List<SonPartition> sonPartition;
    @Transient
    private SysUser sysUsers;
    @Transient
    private List<SysUser> createUser;
    @Transient
    private String newReplyTime;


    public String getNewReplyTime() {
        return newReplyTime;
    }

    public void setNewReplyTime(String newReplyTime) {
        this.newReplyTime = newReplyTime;
    }

    public List<SysUser> getCreateUser() {
        return createUser;
    }

    public void setCreateUser(List<SysUser> createUser) {
        this.createUser = createUser;
    }

    public SysUser getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(SysUser sysUsers) {
        this.sysUsers = sysUsers;
    }

    public List<SonPartition> getSonPartition() {
        return sonPartition;
    }

    public void setSonPartition(List<SonPartition> sonPartition) {
        this.sonPartition = sonPartition;
    }

    /**
     * 获取分区ID
     *
     * @return id - 分区ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置分区ID
     *
     * @param id 分区ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取分区名称
     *
     * @return name - 分区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分区名称
     *
     * @param name 分区名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * 获取是否删除 1是0否
     *
     * @return del_flag - 是否删除 1是0否
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除 1是0否
     *
     * @param delFlag 是否删除 1是0否
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ParentPartition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createBy='" + createBy + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", sonPartition=" + sonPartition +
                ", sysUsers=" + sysUsers +
                ", createUser=" + createUser +
                ", newReplyTime='" + newReplyTime + '\'' +
                '}';
    }
}
