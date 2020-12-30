package com.pmdgjjw.efguser.dao;

import java.util.Set;

/**
 * @auth jian j w
 * @date 2020/8/26 17:16
 * @Description
 */
public class UserMapperSqlPorinter  {

    public String FriendListDetail(Set ids) {

        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT su.id, su.`user_name` , su.head_portrait from sys_user su ");
        sb.append(" WHERE su.id  in ");
        sb.append("( ");
        for (Object id : ids) {
            sb.append(id.toString()+",");
        }

        sb.deleteCharAt(sb.length()-1);

        sb.append(")");
        return sb.toString();
    }
}
