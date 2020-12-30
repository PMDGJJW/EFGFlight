package com.pmdgjjw.efguser.dao;

import com.pmdgjjw.efguser.entity.SysUser;
import com.pmdgjjw.efguser.entity.WKUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @auth jian j w
 * @date 2020/7/27 19:21
 * @Description
 */

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<SysUser> {

    @Select("SELECT " +
            "su.id, " +
            "su.user_name, " +
            "su.user_type, " +
            "su.UID, " +
            "su.head_portrait " +
            "FROM " +
            "EFGUser.sys_user su " +
            "WHERE id = #{id} AND del_flag = 0")
    SysUser selectUserDetail(Long id);


    @Select("SELECT " +
            "su.id, " +
            "su.user_name, " +
            "su.head_portrait " +
            "FROM " +
            "EFGUser.sys_user su " +
            "WHERE id = #{id} AND del_flag = 0")
    WKUser selectUserWK(Long id);

    @Select("SELECT " +
            "id, " +
            "user_name, " +
            "name, " +
            "head_portrait, " +
            "user_type " +
            "FROM " +
            "EFGUser.sys_user  " +
            "WHERE id = #{id} AND del_flag = 0")
    SysUser selectUser(Long id);

    @SelectProvider(type = UserMapperSqlPorinter.class,method ="FriendListDetail" )
    List<WKUser> FridenDetailList(Set<String> set);
}
