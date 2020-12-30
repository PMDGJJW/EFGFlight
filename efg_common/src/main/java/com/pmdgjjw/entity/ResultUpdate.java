package com.pmdgjjw.entity;

import java.io.Serializable;

/**
 * @auth jian j w
 * @date 2020/6/28 23:34
 * @Description
 */
public class ResultUpdate implements Serializable {

    public static Result update(int i){

        Result r = new Result(true,200,"更新成功");
        if (i<0){
            r.setCode(400);
            r.setFlag(false);
            r.setMessage("忽然29节，数据复飞了");
        }
        else if (i>1){
            r.setCode(400);
            r.setFlag(false);
            r.setMessage("对不起，您的用户权限不足");
        }
        return r;

    }

}
