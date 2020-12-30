package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.Thumbdw;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 23:41
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface ThumbDwTackMapper extends Mapper<Thumbdw> {


    @InsertProvider(value = ThunmbDwTackSqlProvider.class,method = "ThumbDwTackString")
    int ThumbDwInsert(List<Thumbdw>list);


}
