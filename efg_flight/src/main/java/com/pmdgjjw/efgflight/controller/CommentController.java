package com.pmdgjjw.efgflight.controller;

import com.github.pagehelper.PageInfo;
import com.pmdgjjw.efgflight.client.FlightClient;
import com.pmdgjjw.efgflight.dao.SpitMongo;
import com.pmdgjjw.efgflight.entity.*;
import com.pmdgjjw.efgflight.service.CommentService;
import com.pmdgjjw.efgflight.service.NewDetailService;
import com.pmdgjjw.efgflight.util.MapChange;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
import com.pmdgjjw.entity.ResultUpdate;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auth jian j w
 * @date 2020/6/27 9:52
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/getComment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private FlightClient flightClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SpitMongo spitResp;

    @Autowired
    private NewDetailService newDetailService;


    @GetMapping("/{page}/{size}/{id}")
    public Result selectCommentALL(@PathVariable int page, @PathVariable int size , @PathVariable int id){

        PageInfo<Comments> pageInfo = commentService.pageInfo(page, size, id);

        return   ResultSelect.Select(pageInfo);
    }

    @GetMapping("/selectByCat/{page}/{size}/{id}/{pid}")
    public Result selectCommentByID(@PathVariable int page, @PathVariable int size ,@PathVariable int id,@PathVariable int pid){

        PageInfo<Comments> pageInfo = commentService.pageInfoByCatID(page, size, id,pid);

        return  ResultSelect.Select(pageInfo);
    }

    @GetMapping("/CommentDetail/{id}")
    public Result selectCommentDetail(@PathVariable int id , HttpServletRequest request ){
        Comments commentDetail = commentService.selectCommentDetail(id,request);
        return ResultSelect.Select(commentDetail);
    }

    @PostMapping("/doInsert")
    public Result commentInsert(@RequestBody Map<String,Object>map){


        List<Object> bean = MapChange.getBean(map);
        Comments com = (Comments) bean.get(0);
        SysUser user = (SysUser) bean.get(1);
        Gold gold = null;
        if (bean.size()==3){
            gold =   (Gold) bean.get(2);
        }


            Integer i = commentService.commentInsert(com, user);

            if (i != 0){
                int i1 = 0;
                if (gold!= null){
                    gold.setCid(i);
                    i1 = flightClient.CommentInSertGold(gold);
                    if (i1>0){

                        return new Result(true, 200, "发布成功");
                    }
                    else {
                        return new Result(false, 409, "发布失败");
                    }
                }else {
                    return new Result(true, 200, "发布成功");
                }


            }else {

                return new Result(false, 409, "发布失败");
            }


    }

    @PostMapping("/doSpit/{id}")
    public Result doSpit(@PathVariable int id, @RequestBody Map<String,Object> map ){

        List<Object> bean = MapChange.getBean(map);

        SysUser su = (SysUser) (bean.get(1));

        Spit spit = (Spit) (bean.get(0));

        Spit doSpit = commentService.doSpit(id, spit, su);

        Map<String,Object> gold = new HashMap<>();

        Result result = new Result();

        gold.put("cid",id);
        gold.put("uid",su.getId());
        result = flightClient.spitChangeGold(gold);

        if (!StringUtils.isEmpty(doSpit.getId())){
            result.setMessage(doSpit.getId());
            String string = null;
            try {
                string = result.getData().toString();
            } catch (Exception e) {
            }

            if ( string .equals("1")){
                doSpit.setgCheck(1);
                Spit save = spitResp.save(doSpit);
            }
        }

        return result;

    }

    @DeleteMapping("/spCheck/{id}")
    public Result sonPartitionCheck(@PathVariable int id ){
        int i = commentService.sonPartitionCheck(id);

        return ResultUpdate.update(i);
    }

    @GetMapping("/getNewComments")
    public Result newComments(){

        List<BaseComment> list = commentService.newComments();

        return ResultSelect.Select(list);
    }

    @PostMapping("/doHotReply/{id}")
    public Result doHot(@PathVariable int id) {

        int i = commentService.doReplyHot(id);

        if (i>0){
            return new Result(true,200,"操作成功");
        }else {
            return new Result(false,400,"操作失败");
        }
    }

    @GetMapping("/doUser/getDetail/{id}")
    public Result selectUserDetail(@PathVariable("id") Long id){
        Result result = flightClient.selectUserDetail(id);

        return result;
    }

    @PostMapping("/mqco")
    public void sendMq(@RequestBody Map<String,Object> map){
        NewInfoDetail newInfoDetail = new NewInfoDetail();

        String cid = "";
        if (map.containsKey("cid")){
            // 信息初始化
            cid = map.get("cid").toString();
            // 帖子Id
            newInfoDetail.setCommentId(Integer.valueOf(map.get("cid").toString()));
        }


        String uid = "";
        if (map.containsKey("uid")){
            uid = map.get("uid").toString();
            // 被评论者Id
            newInfoDetail.setUserId(Long.valueOf(map.get("upid").toString()));
        }
        SysUser result = flightClient.getDetail(Long.valueOf(uid));

        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date());

        BaseComment baseComment = commentService.CommentTitle(Integer.valueOf(cid));

        map.put("date",format);
        map.put("cTitle",baseComment.getTitle());
        // 文章标题
        newInfoDetail.setCommentTitle(baseComment.getTitle());
        map.put("uName",result.getUserName() == null? result.getName(): result.getUserName());
        // 评论者名称
        newInfoDetail.setUserName(result.getUserName() == null? result.getName(): result.getUserName());
        map.put("headp",result.getHeadPortrait());
        // 评论者头像
        newInfoDetail.setUserHead(result.getHeadPortrait());
        newInfoDetail.setMsgInfo(map.get("msg").toString());
        newInfoDetail.setSpitId(map.get("spitId").toString());
        newInfoDetail.setIsRead(0);

        newDetailService.insertNewInfo(newInfoDetail);

        amqpTemplate.convertAndSend("efgTopicExchange","efg.comment",map);

    }

    @PostMapping("/mqre")
    public void sendMqReply(@RequestBody Map<String,Object> map){
        NewInfoDetail newInfoDetail = new NewInfoDetail();
        // 是否查看标识
        newInfoDetail.setIsRead(0);
        // 创建时间
        newInfoDetail.setCreateTime(new Date());
        String uid = "";
        if (map.containsKey("uid")){
            uid = map.get("uid").toString();
        }
        // 查看是否存在spitId 默认为0
        if (map.containsKey("spitId") && map.get("spitId") != null){
            newInfoDetail.setSpitId(map.get("spitId").toString());
            newInfoDetail.setMsgInfo(String.valueOf(map.get("msg")));
        }
        // 动态操作人信息
        SysUser result = flightClient.getDetail(Long.valueOf(uid));
        // 文章id
        Object cid = "";
        if (map.containsKey("cid")){
            cid = map.get("cid");
            // 帖子Id
            newInfoDetail.setCommentId(Integer.valueOf(map.get("cid").toString()));
        }

        String s = cid.toString();

        List<Spit> replyUser = commentService.getReplyUser(Integer.valueOf(s));

        BaseComment baseComment = commentService.CommentTitle(Integer.valueOf(String.valueOf(cid)));
        // 帖子信息
        if (baseComment != null){
            newInfoDetail.setCommentId(Integer.valueOf(s));
            // 帖子标题
            newInfoDetail.setCommentTitle(baseComment.getTitle());
        }
        Set<String> set = new HashSet<>();
        for (Spit spit : replyUser) {
            set.add(spit.getUserid());
        }

        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (result != null){
            map.put("cTitle",baseComment.getTitle());
            map.put("uid",result.getId());
            // 评论者信息
            newInfoDetail.setUserHead(result.getHeadPortrait());
            newInfoDetail.setUserName(result.getUserName() == null? result.getName(): result.getUserName());
            map.put("uName",result.getUserName() == null? result.getName(): result.getUserName());
            map.put("headp",result.getHeadPortrait());
        }
        map.put("data",format);

        for (String n : set) {
            map.put("userid", n);
            newInfoDetail.setUserId(Long.valueOf(n));
            newDetailService.insertNewInfo(newInfoDetail);
            commentService.MqNewThread(map);
        }


    }


}
