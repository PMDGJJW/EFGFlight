package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.PartitionDetail;
import com.pmdgjjw.efgflight.entity.SonPartition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/6/28 16:48
 * @Description
 */

public class SonPartitionSqlProvider {

    public String sonInsert( @Param("list") List<SonPartition> list){

        StringBuilder sb = new StringBuilder();

        sb.append(" INSERT into son_partition (name,icon,create_date,update_date,del_flag,parent_id) VALUES ");

        for (int i = 0; i < list.size(); i++) {

         sb.append(" (");

         sb.append("#{ list["+i+"].name},"+"#{ list["+i+"].icon},"+"#{ list["+i+"].createDate},"
                 +"#{ list["+i+"].updateDate},"+"0,"+"#{ list["+i+"].parentId}"
         );

         sb.append("),");
        }

        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

}
