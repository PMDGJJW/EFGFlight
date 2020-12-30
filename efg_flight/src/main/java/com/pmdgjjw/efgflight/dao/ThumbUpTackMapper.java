package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.ThumbUp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 23:40
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface ThumbUpTackMapper extends Mapper<ThumbUp> {

    @InsertProvider(value = ThumbUpTackSqlProvider.class,method = "ThumbUpTackString")
    int thumbUpInsert(List<ThumbUp> list);

}
