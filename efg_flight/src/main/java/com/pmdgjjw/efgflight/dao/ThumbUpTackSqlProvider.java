package com.pmdgjjw.efgflight.dao;

import com.pmdgjjw.efgflight.entity.ThumbUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/12 23:43
 * @Description
 */
public class ThumbUpTackSqlProvider {

    public String ThumbUpTackString(@Param("list") List<ThumbUp> list){

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT IGNORE INTO thumbup (sid,uid) VALUES ");
        for (int i = 0; i < list.size(); i++) {

            sb.append("( #{ list["+i+"].sid}"+", #{list["+i+"].uid"+" }),");

        }

        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

}
