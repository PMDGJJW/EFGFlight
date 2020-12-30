package com.pmdgjjw.efguser.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "EFGUser.sys_user")
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String name;

    @Transient
    private String voId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户权限
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 用户唯一UID
     */
    @Column(name = "UID")
    private Long uid;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 注册手机号
     */
    private String tel;

    /**
     * 邮箱验证
     */
    @Column(name = "email_check")
    private Integer emailCheck;

    /**
     * 手机验证
     */
    @Column(name = "tel_check")
    private Integer telCheck;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 好友数
     */
    @Column(name = "friend_count")
    private Integer friendCount;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private Integer replyCount;

    /**
     * 主题数
     */
    @Column(name = "theme_count")
    private Integer themeCount;

    /**
     * 喜爱机型
     */
    @Column(name = "favourite_plane")
    private String favouritePlane;

    /**
     * 注册时间
     */
    @Column(name = "regiest_time")
    private Date regiestTime;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    private Date replyTime;

    /**
     * 注册IP
     */
    @Column(name = "regis_ip")
    private String regisIp;

    /**
     * 已用空间
     */
    @Column(name = "use_space")
    private Integer useSpace;

    /**
     * 用户积分
     */
    @Column(name = "user_integral")
    private Integer userIntegral;

    /**
     * 用户威望
     */
    @Column(name = "user_prestige")
    private Integer userPrestige;

    /**
     * 用户金币
     */
    @Column(name = "user_gold")
    private Integer userGold;

    /**
     * 用户爱心
     */
    @Column(name = "user_heart")
    private Integer userHeart;

    /**
     * 用户支持
     */
    @Column(name = "user_support")
    private Integer userSupport;

    /**
     * 是否删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 头像
     */
    @Column(name = "head_portrait")
    private String headPortrait;

    /**
     * 出生日期
     */
    private Date birthday;

    private String address;

    /**
     * 举报数
     */
    @Column(name = "report_count")
    private Integer reportCount;

    /**
     * 投诉数
     */
    @Column(name = "complain_count")
    private Integer complainCount;




    /**
     * 获取用户ID
     *
     * @return id - 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户ID
     *
     * @param id 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SysUser() {
    }

    public String getVoId() {
        return voId;
    }

    public void setVoId(String voId) {
        this.voId = voId;
    }

    public SysUser(Long id, String name, String voId, String userName, Integer userType, Long uid, String email, String tel, Integer emailCheck, Integer telCheck, String password, Integer sex, Integer friendCount, Integer replyCount, Integer themeCount, String favouritePlane, Date regiestTime, Date loginTime, Date replyTime, String regisIp, Integer useSpace, Integer userIntegral, Integer userPrestige, Integer userGold, Integer userHeart, Integer userSupport, Integer delFlag, String headPortrait, Date birthday, String address, Integer reportCount, Integer complainCount) {
        this.id = id;
        this.name = name;
        this.voId = voId;
        this.userName = userName;
        this.userType = userType;
        this.uid = uid;
        this.email = email;
        this.tel = tel;
        this.emailCheck = emailCheck;
        this.telCheck = telCheck;
        this.password = password;
        this.sex = sex;
        this.friendCount = friendCount;
        this.replyCount = replyCount;
        this.themeCount = themeCount;
        this.favouritePlane = favouritePlane;
        this.regiestTime = regiestTime;
        this.loginTime = loginTime;
        this.replyTime = replyTime;
        this.regisIp = regisIp;
        this.useSpace = useSpace;
        this.userIntegral = userIntegral;
        this.userPrestige = userPrestige;
        this.userGold = userGold;
        this.userHeart = userHeart;
        this.userSupport = userSupport;
        this.delFlag = delFlag;
        this.headPortrait = headPortrait;
        this.birthday = birthday;
        this.address = address;
        this.reportCount = reportCount;
        this.complainCount = complainCount;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name ;
    }

    /**
     * 获取用户名称
     *
     * @return user_name - 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户权限
     *
     * @return user_type - 用户权限
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户权限
     *
     * @param userType 用户权限
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取用户唯一UID
     *
     * @return UID - 用户唯一UID
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户唯一UID
     *
     * @param uid 用户唯一UID
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取注册邮箱
     *
     * @return email - 注册邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置注册邮箱
     *
     * @param email 注册邮箱
     */
    public void setEmail(String email) {
        this.email = email ;
    }

    /**
     * 获取注册手机号
     *
     * @return tel - 注册手机号
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置注册手机号
     *
     * @param tel 注册手机号
     */
    public void setTel(String tel) {
        this.tel = tel ;
    }

    /**
     * 获取邮箱验证
     *
     * @return email_check - 邮箱验证
     */
    public Integer getEmailCheck() {
        return emailCheck;
    }

    /**
     * 设置邮箱验证
     *
     * @param emailCheck 邮箱验证
     */
    public void setEmailCheck(Integer emailCheck) {
        this.emailCheck = emailCheck;
    }

    /**
     * 获取手机验证
     *
     * @return tel_check - 手机验证
     */
    public Integer getTelCheck() {
        return telCheck;
    }

    /**
     * 设置手机验证
     *
     * @param telCheck 手机验证
     */
    public void setTelCheck(Integer telCheck) {
        this.telCheck = telCheck;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password ;
    }

    /**
     * 获取用户性别
     *
     * @return sex - 用户性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置用户性别
     *
     * @param sex 用户性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取好友数
     *
     * @return friend_count - 好友数
     */
    public Integer getFriendCount() {
        return friendCount;
    }

    /**
     * 设置好友数
     *
     * @param friendCount 好友数
     */
    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    /**
     * 获取回复数
     *
     * @return reply_count - 回复数
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * 设置回复数
     *
     * @param replyCount 回复数
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * 获取主题数
     *
     * @return theme_count - 主题数
     */
    public Integer getThemeCount() {
        return themeCount;
    }

    /**
     * 设置主题数
     *
     * @param themeCount 主题数
     */
    public void setThemeCount(Integer themeCount) {
        this.themeCount = themeCount;
    }

    /**
     * 获取喜爱机型
     *
     * @return favourite_plane - 喜爱机型
     */
    public String getFavouritePlane() {
        return favouritePlane;
    }

    /**
     * 设置喜爱机型
     *
     * @param favouritePlane 喜爱机型
     */
    public void setFavouritePlane(String favouritePlane) {
        this.favouritePlane = favouritePlane ;
    }

    /**
     * 获取注册时间
     *
     * @return regiest_time - 注册时间
     */
    public Date getRegiestTime() {
        return regiestTime;
    }

    /**
     * 设置注册时间
     *
     * @param regiestTime 注册时间
     */
    public void setRegiestTime(Date regiestTime) {
        this.regiestTime = regiestTime;
    }

    /**
     * 获取登录时间
     *
     * @return login_time - 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登录时间
     *
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取回复时间
     *
     * @return reply_time - 回复时间
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置回复时间
     *
     * @param replyTime 回复时间
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取注册IP
     *
     * @return regis_ip - 注册IP
     */
    public String getRegisIp() {
        return regisIp;
    }

    /**
     * 设置注册IP
     *
     * @param regisIp 注册IP
     */
    public void setRegisIp(String regisIp) {
        this.regisIp = regisIp ;
    }

    /**
     * 获取已用空间
     *
     * @return use_space - 已用空间
     */
    public Integer getUseSpace() {
        return useSpace;
    }

    /**
     * 设置已用空间
     *
     * @param useSpace 已用空间
     */
    public void setUseSpace(Integer useSpace) {
        this.useSpace = useSpace;
    }

    /**
     * 获取用户积分
     *
     * @return user_integral - 用户积分
     */
    public Integer getUserIntegral() {
        return userIntegral;
    }

    /**
     * 设置用户积分
     *
     * @param userIntegral 用户积分
     */
    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }

    /**
     * 获取用户威望
     *
     * @return user_prestige - 用户威望
     */
    public Integer getUserPrestige() {
        return userPrestige;
    }

    /**
     * 设置用户威望
     *
     * @param userPrestige 用户威望
     */
    public void setUserPrestige(Integer userPrestige) {
        this.userPrestige = userPrestige;
    }

    /**
     * 获取用户金币
     *
     * @return user_gold - 用户金币
     */
    public Integer getUserGold() {
        return userGold;
    }

    /**
     * 设置用户金币
     *
     * @param userGold 用户金币
     */
    public void setUserGold(Integer userGold) {
        this.userGold = userGold;
    }

    /**
     * 获取用户爱心
     *
     * @return user_heart - 用户爱心
     */
    public Integer getUserHeart() {
        return userHeart;
    }

    /**
     * 设置用户爱心
     *
     * @param userHeart 用户爱心
     */
    public void setUserHeart(Integer userHeart) {
        this.userHeart = userHeart;
    }

    /**
     * 获取用户支持
     *
     * @return user_support - 用户支持
     */
    public Integer getUserSupport() {
        return userSupport;
    }

    /**
     * 设置用户支持
     *
     * @param userSupport 用户支持
     */
    public void setUserSupport(Integer userSupport) {
        this.userSupport = userSupport;
    }

    /**
     * 获取是否删除
     *
     * @return del_flag - 是否删除
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除
     *
     * @param delFlag 是否删除
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取头像
     *
     * @return head_portrait - 头像
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * 设置头像
     *
     * @param headPortrait 头像
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取举报数
     *
     * @return report_count - 举报数
     */
    public Integer getReportCount() {
        return reportCount;
    }

    /**
     * 设置举报数
     *
     * @param reportCount 举报数
     */
    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    /**
     * 获取投诉数
     *
     * @return complain_count - 投诉数
     */
    public Integer getComplainCount() {
        return complainCount;
    }

    /**
     * 设置投诉数
     *
     * @param complainCount 投诉数
     */
    public void setComplainCount(Integer complainCount) {
        this.complainCount = complainCount;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", uid=" + uid +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", emailCheck=" + emailCheck +
                ", telCheck=" + telCheck +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", friendCount=" + friendCount +
                ", replyCount=" + replyCount +
                ", themeCount=" + themeCount +
                ", favouritePlane='" + favouritePlane + '\'' +
                ", regiestTime=" + regiestTime +
                ", loginTime=" + loginTime +
                ", replyTime=" + replyTime +
                ", regisIp='" + regisIp + '\'' +
                ", useSpace=" + useSpace +
                ", userIntegral=" + userIntegral +
                ", userPrestige=" + userPrestige +
                ", userGold=" + userGold +
                ", userHeart=" + userHeart +
                ", userSupport=" + userSupport +
                ", delFlag=" + delFlag +
                ", headPortrait='" + headPortrait + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", reportCount=" + reportCount +
                ", complainCount=" + complainCount +
                '}';
    }
}
