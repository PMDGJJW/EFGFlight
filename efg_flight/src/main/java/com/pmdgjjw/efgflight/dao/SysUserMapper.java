package com.pmdgjjw.efgflight.dao;


import com.pmdgjjw.efgflight.entity.SysUser;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SysUserMapper extends Mapper<SysUser> {

    @Select("select su.user_name , su.id from EFGUser.sys_user su WHERE su.id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userName",column = "user_name")
    })
    List<SysUser> selectUseUserID(int id);

    @Select("SELECT user_type FROM EFGUser.sys_user WHERE id = #{id}")
    Integer typeCheck(Long id);

}
