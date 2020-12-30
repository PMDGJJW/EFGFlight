package com.pmdgjjw.efgflight.dao;


import com.pmdgjjw.efgflight.entity.ParentPartition;
import com.pmdgjjw.efgflight.entity.PartitionInfo;
import com.pmdgjjw.efgflight.entity.SysUser;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface PartitionMapper extends Mapper<ParentPartition> {

    /*
     * @Description 论坛首页分区详情
     * @author jian j w
     * @date 2020/6/26
     * @param null
     * @return
     */
    @Select(" select pp.id parentId ,pp.name parentName from parent_partition pp where del_flag = '0' ")
    @Results({
            @Result(property = "id", column = "parentId"),
            @Result(property = "name", column = "parentName"),
            @Result(column = "parentId", property = "sonPartition", many = @Many(select = "com.pmdgjjw.efgflight.dao.SonPartitionMapper.selectSonALLInIndex"))
    })
    List<ParentPartition> selectAllInIndex();

    @Select(" select pp.id parentId ,pp.name parentName from parent_partition pp where id = #{id} and del_flag = '0'")
    @Results({
            @Result(id = true,property = "id", column = "parentId"),
            @Result(property = "name", column = "parentName"),
            @Result(column = "parentId", property = "sonPartition" ,many = @Many (select = "com.pmdgjjw.efgflight.dao.SonPartitionMapper.selectSonByIdInIndex"))
    })
    ParentPartition selectByIdInIndex(int id);

    @Select("select pd.user_id,pp.id ,pp.`name` from parent_partition pp , partiton_detail pd where pp.id = pd.partition_id  and pp.del_flag = '0'")
    @Results({
            @Result(id = true ,property = "id",column = "id" ),
            @Result(property = "name",column = "name"),
            @Result(property = "sysUsers",column = "user_id",many = @Many(select = "com.pmdgjjw.efgflight.dao.SysUserMapper.selectUseUserID"))
    })
    List<ParentPartition>selectUserID();

    @Select("select pd.user_id,pp.id ,pp.`name` from parent_partition pp , partiton_detail pd where pp.id = pd.partition_id  and pp.del_flag = '0' and pp.id = #{id}")
    @Results({
            @Result(id = true ,property = "id",column = "id" ),
            @Result(property = "name",column = "name"),
            @Result(property = "sysUsers",column = "user_id",many = @Many(select = "com.pmdgjjw.efgflight.dao.SysUserMapper.selectUseUserID"))
    })
    List<ParentPartition>selectUserIDByKey(int id);

    @Insert("INSERT INTO parent_partition (name,create_date,update_date,create_by,del_flag ) VALUES(#{name},#{createDate},#{updateDate},#{createBy},#{delFlag})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insertPartition(ParentPartition partition);

    @Select("select pp.id from partiton_detail pd , parent_partition pp WHERE pd.partition_id = pp.id and pd.user_id = #{user.id} and pp.`name` = #{partition.name}")
    String PartitionDetailCheck(@Param("user") SysUser user, @Param("partition") ParentPartition partition);

    @Insert("INSERT INTO partiton_detail (user_id, partition_id) VALUES (#{user.id},#{partition.id})")
    int insertPartitionDetail(@Param("user") SysUser user ,@Param("partition") ParentPartition partition);

    @Select(" select id from parent_partition where del_flag = '0' ")
    List<Integer> selectID();

    @Select(" select name,id from parent_partition where id = #{pid} and del_flag = '0' ")
    PartitionInfo selectNameFromId(int pid);
}
