package com.pmdgjjw.efgflight.dao;

import java.util.List;

public class NewDetailPartitionSqlProvider {

    public String updateIsRead(List<Long> id){
        StringBuilder sb = new StringBuilder();
        sb.append("update  newInfoDetail SET is_read = 1 WHERE id in  ");
        sb.append("(");
        for (Long aLong : id) {
            sb.append(aLong);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

}
