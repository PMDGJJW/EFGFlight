package com.pmdgjjw.entity;

import java.io.Serializable;

/**
 * @auth jian j w
 * @date 2020/6/28 23:11
 * @Description
 */
public class ResultSelect implements Serializable {

    public static Result Select(Object o){

        Result r = new Result(true,200,"查询成功");
        if (o==null){
            r.setCode(400);
            r.setFlag(false);
            r.setMessage("忽然30节，数据复飞了");
            return r;
        }
        else {
            r.setData(o);
            return r;
        }

    }
}
