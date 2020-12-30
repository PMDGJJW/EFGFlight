package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.NewInfoDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface NewDetailMapper extends Mapper<NewInfoDetail> {

    @UpdateProvider(type = NewDetailPartitionSqlProvider.class,method = "updateIsRead")
    int updateIsRead( @Param("id") List<Long>  id);


}
