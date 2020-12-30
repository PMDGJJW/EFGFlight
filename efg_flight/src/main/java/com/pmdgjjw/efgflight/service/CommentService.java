package com.pmdgjjw.efgflight.service;


import com.github.pagehelper.PageInfo;
import com.pmdgjjw.efgflight.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CommentService extends BaseService<Comments>{

    PageInfo<Comments> pageInfo(int page , int size , int id);

    PageInfo<Comments> pageInfoByCatID(int page , int size , int id,int pid);

    int commentInsert(Comments comment , SysUser user);

    Comments selectCommentDetail(int id , HttpServletRequest request);

    Spit doSpit(int id , Spit spit, SysUser user);

    int countTopicByPid(int pid);

    int countItemByPid(int pid);

    int sonPartitionCheck(int id);

    List<BaseComment> newComments();

    List<BaseComment> newReplyComments();

    List<BaseComment> dayHotComments();

    List<BaseComment> weekHotComments();

    BaseComment newCommentDeatail(int id);

    int doReplyHot(int id);

    BaseComment getIndexCommentDetail(int id);

    List<Spit> getReplyUser(int id);

    void MqNewThread( Map<String,Object> map);

    BaseComment CommentTitle(int id);
}
