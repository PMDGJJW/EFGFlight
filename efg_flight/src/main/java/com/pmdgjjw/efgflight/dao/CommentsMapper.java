package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.BaseComment;
import com.pmdgjjw.efgflight.entity.Comments;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface CommentsMapper extends Mapper<Comments> {

    @Select(" SELECT com.reply_name , com.id comment_id , com.suid , com.regiest_time , com.title , ct.categort_name , ct.id categort_id , com.see_count , com.ishot ,com.issurper ,com.isapprove ," +
            " com.reply_count  , com.create_time , com.comment_time  , sur.user_name userName ,sur.id user_id , sur.head_portrait " +
            " FROM (select su.head_portrait , su.regiest_time , su.user_name reply_name , su.user_type ,su.id suid,co.id " +
            " , co.title , co.see_count , co.reply_count , co.ishot ,co.issurper ,co.isapprove , co.parent_id , co.del_flag , co.categort , co.create_time , co.comment_time ,co.create_by " +
            " from comments co LEFT JOIN EFGUser.sys_user  su on  co.newreply_id = su.id ) com ,EFGUser.sys_user sur , categort_type ct " +
            " WHERE com.create_by = sur.id and com.categort = ct.id and com.del_flag = '0' and com.isapprove = '1' and com.parent_id = #{id} ")
    @Results({
            @Result(property = "id",column = "comment_id"),
            @Result(property = "createName",column = "userName"),
            @Result(property = "newreplyId",column = "suid"),
            @Result(property = "sysUser.id",column = "user_id"),
            @Result(property = "sysUser.userName",column = "userName"),
            @Result(property = "sysUser.regiestTime",column = "regiest_time"),
            @Result(property = "sysUser.headPortrait",column = "head_portrait")
    })
    List<Comments> selectAllComments(int id);

    @Select(" SELECT com.reply_name , com.id comment_id , com.regiest_time , com.suid , com.title , ct.categort_name , ct.id categort_id , com.see_count , com.ishot ,com.issurper ,com.isapprove ," +
            " com.reply_count  , com.create_time , com.comment_time  , sur.user_name userName ,sur.id user_id , sur.head_portrait " +
            " FROM (select su.head_portrait ,su.regiest_time ,su.user_name reply_name , su.user_type ,su.id suid,co.id " +
            " , co.title , co.see_count , co.reply_count , co.ishot ,co.issurper ,co.isapprove , co.parent_id , co.del_flag , co.categort , co.create_time , co.comment_time ,co.create_by " +
            " from comments co LEFT JOIN EFGUser.sys_user  su on  co.newreply_id = su.id ) com ,EFGUser.sys_user sur , categort_type ct " +
            " WHERE com.create_by = sur.id and com.categort = ct.id and com.del_flag = '0' and com.isapprove = '1' and com.parent_id = #{pid} and com.categort = #{id} ")
    @Results({
            @Result(property = "id",column = "comment_id"),
            @Result(property = "createName",column = "userName"),
            @Result(property = "newreplyId",column = "suid"),
            @Result(property = "sysUser.id",column = "user_id"),
            @Result(property = "sysUser.userName",column = "userName"),
            @Result(property = "sysUser.regiestTime",column = "regiest_time"),
            @Result(property = "sysUser.headPortrait",column = "head_portrait")
    })
    List<Comments> selectByCartId(int id,int pid);

    @Select("select co.id, " +
            "co.title, " +
            "co.ishot, " +
            "co.issurper, " +
            "co.isapprove, " +
            "co.reward, " +
            "co.categort, "+
            "co.create_time, " +
            "co.update_time, " +
            "co.comment_time, " +
            "co.reply_count, " +
            "co.see_count, " +
            "co.`comment`, " +
            "ct.categort_name ,su.user_name  ,su.id uid from comments co INNER JOIN " +
            "categort_type ct INNER JOIN EFGUser.sys_user su  where co.is_band = '0' and co.id = #{id} and co.categort = ct.id and co.create_by = su.id ")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "categort_name",property = "cateName"),
            @Result(property = "sysUser.id",column = "uid"),
            @Result(property = "sysUser.userName",column = "user_name")
    })
    Comments selectCommentDetail(int id);

    @Update("UPDATE comments SET comment_time = #{commentTime},newreply_id = #{newreplyId},reply_count = #{replyCount} WHERE id = #{id}")
    int updateAfterSpit( Comments comments);

    @Select("  select count(id) from comments where del_flag = '0' and isapprove = '1' and categort = #{pid}  ")
    int countByPid(int pid);

    @Select("select com.id, com.title,sy.id uid ,sy.user_name from comments com LEFT JOIN EFGUser.sys_user sy on com.create_by = sy.id and com.del_flag = 0  ORDER BY com.create_time DESC LIMIT 0,8")
    List<BaseComment> newComments();

    @SelectProvider(type = CommentsPartitionSqlProvider.class,method = "doHotComment")
    List<BaseComment> hotComments(@Param("id") List<String> id);

    @Select("select com.id, com.title,com.create_time,com.comment_time ,com.reply_count , ct.categort_name FROM comments com INNER JOIN categort_type ct on com.categort = ct.id " +
            "and com.id = #{id} and com.del_flag = 0 ")
    BaseComment selectNewCommentDetail(int id);

    @Select("SELECT com.id, com.title ,sy.id uid,sy.user_name, com.comment_time from comments com LEFT JOIN EFGUser.sys_user sy on com.newreply_id = sy.id WHERE com.del_flag = 0 ORDER BY com.comment_time DESC LIMIT 0,8")
    List<BaseComment> newReplyComments();

    @Select("SELECT com.id, com.title  from comments com WHERE id = #{id} and del_flag = 0  ")
    BaseComment CommentsTitle(int id);

}
