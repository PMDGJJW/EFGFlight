package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.Thumbdw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 23:43
 * @Description
 */
public class ThunmbDwTackSqlProvider {

    public String ThumbDwTackString(@Param("list") List<Thumbdw> list){

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT IGNORE INTO thumbdw (sid,uid) VALUES ");
        for (int i = 0; i < list.size(); i++) {

            sb.append("( #{ list["+i+"].sid}"+", #{list["+i+"].uid"+" }),");

        }

        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

}
