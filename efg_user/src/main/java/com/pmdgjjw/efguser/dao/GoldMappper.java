package com.pmdgjjw.efguser.dao;

import com.pmdgjjw.efguser.entity.Gold;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;


/**
 * @auth jian j w
 * @date 2020/7/7 10:17
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface GoldMappper extends Mapper<Gold> {

    @Update("UPDATE gold SET gold_count = #{Count} WHERE id = #{id} ")
    int updateByCount(@Param("id") int id, @Param("Count") int Count);


}
