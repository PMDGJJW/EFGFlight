package com.pmdgjjw.efgflight.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/7/26 16:48
 * @Description
 */
public class CommentsPartitionSqlProvider {

    public String doHotComment(@Param("id") List<String> id ){

        StringBuilder idsb = new StringBuilder();

        for (int i = 0; i < id.size(); i++) {
            idsb.append("#{"+"id["+i+"]},");
        }
        idsb.deleteCharAt(idsb.length()-1);
        StringBuilder sb = new StringBuilder();

        sb.append("select com.id, com.title,sy.id uid ,sy.user_name from comments com Inner JOIN EFGUser.sys_user sy on com.create_by = sy.id " +
                "and com.del_flag = 0 and com.id in ");
        sb.append("( ");
        sb.append(idsb.toString());
        sb.append(")");

        return sb.toString();

    }

}
