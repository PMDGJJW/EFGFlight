package com.pmdgjjw.efguser.dao;


import com.pmdgjjw.efguser.entity.GoldCheck;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 9:57
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface  GoldCheckMapper extends Mapper<GoldCheck> {

    @InsertProvider(value = GoldTackSqlProvider.class,method = "SpitTackString")
    int SpitTackInsert(List<GoldCheck> list);
}
