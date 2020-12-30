package com.pmdgjjw.efguser.dao;

import com.pmdgjjw.efguser.entity.UserGold;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @auth jian j w
 * @date 2020/7/8 9:48
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface UserGoldMapper extends Mapper<UserGold> {

    @Update("UPDATE user_gold SET gprice = #{newPrice} WHERE uid = #{uid} ")
    int updateByUid(@Param("uid") Long uid, @Param("newPrice") int newPrice);

}
