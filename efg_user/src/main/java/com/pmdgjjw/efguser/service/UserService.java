package com.pmdgjjw.efguser.service;

import com.pmdgjjw.efguser.entity.*;
import com.pmdgjjw.entity.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/27 17:24
 * @Description
 */

public interface UserService extends BaseService<SysUser>{

    Integer getCode(MailUser mailUser);

    int doRegiest(SysUser sysUser, String mailCode, HttpServletRequest request);

    int doMailCodeCheck(SysUser sysUser , String mailCode);

    Map<String,Object> doLogin(String name , String passWord);

    SysUser selectUserDetail(Long id);

    Result SpitChangeGold(Integer cid, Long uid);

    int CommentInsertGold(Gold gold);

    SysUser selectAllUserDetail(Long id);

    Float userDetailPresent(Long uid);

    int userDetailUpdate(SysUser sysUser);

    void updateReplyTime(Long id);

    Result emailCheck(MailUser mailUser);

    Result eCheckCode(String token);

    List<WkMsg> getChatList(String uid,String tid);

    Result addFirendRequest(Long id,Long uid,Map<String,Object> map);

    Result checKFriendRequest(Long id,Long uid,Map<String,Object> map);

    List<WKUser>  getFriendList(Long id);



}
