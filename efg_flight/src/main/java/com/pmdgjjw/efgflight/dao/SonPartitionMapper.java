package com.pmdgjjw.efgflight.dao;


import com.pmdgjjw.efgflight.entity.Comments;
import com.pmdgjjw.efgflight.entity.PartitionDetail;
import com.pmdgjjw.efgflight.entity.PartitionInfo;
import com.pmdgjjw.efgflight.entity.SonPartition;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface SonPartitionMapper extends Mapper<SonPartition> {

    @Select(" select sp.id sonId , sp.name sonName,sp.update_date ,sp.comments,sp.topics ,sp.hot_count " +
            " from son_partition sp where parent_id = #{id} and del_flag = 0 ")
    @Results({
            @Result(property = "id",column = "sonId"),
            @Result(property = "name",column = "sonName")
    })
    SonPartition selectSonALLInIndex(Long id);

    @Select(" select sp.id sonId , sp.icon ,sp.create_by ,sp.new_comment ,sp.name sonName,sp.update_date ,sp.comments,sp.topics ,sp.hot_count " +
            " from son_partition sp where parent_id = #{id} and del_flag = 0 ")
    @Results({
            @Result(property = "id",column = "sonId"),
            @Result(property = "name",column = "sonName")
    })
    SonPartition selectSonByIdInIndex(Long id);

    @Select(" select sp.id  , sp.name , sp.icon  " +
            " from son_partition sp where parent_id = #{id} and del_flag = 0 ")
    List<SonPartition> selectALLByParentID (Long id);

    @InsertProvider(type = SonPartitionSqlProvider.class, method = "sonInsert")
    int SonPartitionInsert(@Param("list") List<SonPartition> list);

    @Update("UPDATE son_partition SET update_date = #{updateDate},create_by = #{createBy} WHERE id = #{id}")
    int updateSonAfterSpit( SonPartition sonPartition);

    @Select(" select id from son_partition where del_flag = '0'  ")
    List<Integer> selectID();

    @Select(" SELECT `name` , id FROM son_partition WHERE parent_id = #{pid} and del_flag = '0'  ")
    List<PartitionInfo> selectDetailByPid(int pid);

    @Select(" SELECT `categort_name` , id FROM categort_type WHERE partition_id = #{pid}   ")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "categort_name")
    })
    List<PartitionInfo> selectDetailByCid(int pid);


}
