package com.pmdgjjw.efguser.dao;


import com.pmdgjjw.efguser.entity.GoldCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 14:00
 * @Description
 */
public class GoldTackSqlProvider {


    public String SpitTackString(@Param("list") List<GoldCheck>list){

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT IGNORE INTO gold_check (cid,uid) VALUES ");
        for (int i = 0; i < list.size(); i++) {

            sb.append("( #{ list["+i+"].cid}"+", #{list["+i+"].uid"+" }),");

        }

        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }


}
