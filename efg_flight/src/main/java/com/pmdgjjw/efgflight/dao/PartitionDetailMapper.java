package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.PartitionDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @auth jian j w
 * @date 2020/6/28 16:07
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface PartitionDetailMapper extends Mapper<PartitionDetail> {

    @Select("SELECT * from partiton_detail WHERE user_id = #{id} and partition_id = #{pid}")
    PartitionDetail checkInDetail(@Param("id") Long id ,@Param("pid") Long pid);

}
