package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.efgflight.service.SpitService;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/12 21:49
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    @GetMapping("/thumbup/{sid}/{uid}")
    public Result doThumbUp(@PathVariable String sid, @PathVariable String uid){
        spitService.delThumbDw(sid,uid);
        int i = spitService.doThumbUp(sid,uid);

        Result result = new Result();
        result.setFlag(true);
        result.setCode(200);

        if (i >0 && i==1){
         result.setMessage("点赞成功");
         result.setData(1);
        }else if (i >0 && i==2){
            int i1 = spitService.delThumbUp(sid,uid);
            if (i1 >0){
                result.setMessage("已取消点赞");
                result.setData(2);
            }else {
                result.setFlag(false);
                result.setCode(400);
                result.setMessage("哦吼，出现异常了");
            }

        }
        else {
            result.setFlag(false);
            result.setCode(400);
            result.setMessage("哦吼，出现异常了");
        }

        return result;

    }

    @GetMapping("/thumbDw/{sid}/{uid}")
    public Result doThumbDw(@PathVariable String sid ,@PathVariable String uid ){
        spitService.delThumbUp(sid,uid);
        int i = spitService.doThumbDw(sid,uid);

        Result result = new Result();
        result.setFlag(true);
        result.setCode(200);

        if (i >0 && i==1){
            result.setMessage("点踩成功");
            result.setData(1);
        }else if (i >0 && i==2){

            int i1 = spitService.delThumbDw(sid,uid);
            if (i1 >0){
                result.setMessage("已取消点踩");
                result.setData(2);
            }else {
                result.setFlag(false);
                result.setCode(400);
                result.setMessage("哦吼，出现异常了");
            }
        }
        else {
            result.setFlag(false);
            result.setCode(400);
            result.setMessage("哦吼，出现异常了");
        }

        return result;
    }

    @GetMapping("/spitSelect/{page}/{uid}/{cid}/{size}")
    public Result spitSelect(@PathVariable int page, @PathVariable int uid, @PathVariable int cid,@PathVariable int size) {


        Map<String, Object> map = spitService.selectSpit(page, uid, cid,size);
        return ResultSelect.Select(map);
    }

}
