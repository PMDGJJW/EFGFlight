package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.efgflight.entity.NewInfoDetail;
import com.pmdgjjw.efgflight.service.NewDetailService;
import com.pmdgjjw.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/newCenter")
public class NewDetailController {

    @Autowired
    private NewDetailService newDetailService;


    @PostMapping("/getNewList")
    public Result getNewList(@RequestBody NewInfoDetail newInfoDetail){

        List<NewInfoDetail> newInfoForCommentId = newDetailService.getNewInfoForCommentId(newInfoDetail);

        return new Result(true,200,"查询成功",newInfoForCommentId);

    }

    @PostMapping("/getNewInfo/{id}")
    public Result getNewInfo(@PathVariable("id") String id){

        Map<String, Object> pageForSpit = newDetailService.getPageForSpit(id);


        return new Result(true,200,"查询成功",pageForSpit);

    }

    @PostMapping("/doDeleteNew/{id}")
    public Result doDeleteNew(@PathVariable("id") String id){

        int i = newDetailService.deleteByPrimaryKey(id);

        if (i>0){
            return new Result(true,200,"删除成功");
        }else {
            return new Result(true,400,"删除失败");
        }
    }

    @PostMapping("/getNewListCount")
    public Result getNewListCount(@RequestBody NewInfoDetail newInfoDetail){

        if (newInfoDetail != null && newInfoDetail.getUserId() != null ){
            Integer newInfoCount = newDetailService.getNewInfoCount(newInfoDetail);
            return new Result(true,200,"查询成功",newInfoCount);
        }else {
            return new Result(true,200,"查询成功",0);
        }


    }

    @PostMapping("/setAllIsRead")
    public Result setAllIsRead(@RequestParam("ids") List<Long> ids){

        Integer integer = newDetailService.setMessageAllRead(ids);

        if (integer >0){
            return new Result(true,200,"操作成功",1);
        }else {
            return new Result(false,400,"操作失败",0);
        }

    }

}
