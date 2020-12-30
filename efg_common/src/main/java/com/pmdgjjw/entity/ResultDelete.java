package com.pmdgjjw.entity;

/**
 * @auth jian j w
 * @date 2020/6/29 0:01
 * @Description
 */
public class ResultDelete {

    public static Result delete(int i){

        Result r = new Result(true,200,"删除成功");
        if (i<0){
            r.setCode(400);
            r.setFlag(false);
            r.setMessage("忽然31节，数据复飞了");
        }
        else if (i>1){
            r.setCode(400);
            r.setFlag(false);
            r.setMessage("对不起，您的用户权限不足");
        }
        return r;

    }

}
