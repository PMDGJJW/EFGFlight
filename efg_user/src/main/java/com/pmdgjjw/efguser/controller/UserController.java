package com.pmdgjjw.efguser.controller;

import com.alibaba.fastjson.JSON;
import com.pmdgjjw.efguser.WebSockerServer;
import com.pmdgjjw.efguser.dao.UserMapper;
import com.pmdgjjw.efguser.entity.*;
import com.pmdgjjw.efguser.service.UserService;
import com.pmdgjjw.entity.Result;
import com.pmdgjjw.entity.ResultSelect;
import com.pmdgjjw.util.JwtUtil;
import io.goeasy.GoEasy;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auth jian j w
 * @date 2020/7/27 12:46
 * @Description
 */
@CrossOrigin
@RestController
@RequestMapping("/doUser")
public class UserController {

    @Autowired
    private UserService sysUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Result getCode(@RequestBody MailUser mailUser){

        SysUser sysUser = new SysUser();
        // 检查邮箱是否被注册
        sysUser.setEmail(mailUser.getEmail());
        List<SysUser> select = sysUserService.select(sysUser);
        if (select.size()>0){
            return new Result(true,400,"该邮箱已经被注册了");
        }else {
            Integer code = sysUserService.getCode(mailUser);

            if (code>0){
                return new Result(true,200,"验证码发送成功");
            }else {
                return new Result(false,400,"验证码发送失败");
            }
        }

    }

    @PostMapping("/doRegiest/{code}")
    public Result doRegiest(@RequestBody SysUser sysUser , @PathVariable String code, HttpServletRequest request){

        int i = sysUserService.doMailCodeCheck(sysUser, code);

        if (i == 1){
            i = sysUserService.doRegiest(sysUser, code,request);
        }

        if (i>0 && i!=2){
            return new Result(true,200,"注册成功");
        }
        else if (i>0 && i==2){
            return new Result(false,400,"注册失败,验证码不正确");
        }else if (i>0 && i==3){
            return new Result(false,400,"注册失败,用户名已存在");
        }
        else {
            return new Result(false,400,"注册失败");
        }

    }

    @PostMapping("/login")
    public Result doLogin(@RequestBody SysUser sysUser){

        Map<String, Object> map = sysUserService.doLogin(sysUser.getName(), sysUser.getPassword());

        if (map == null){
            return new Result(false,400,"用户名或密码错误");
        }
        else {
            return new Result(true,200,"登录成功",map);
        }

    }

    @GetMapping("/getDetail/{id}")
    public Result selectUserDetail(@PathVariable("id") Long id){

        SysUser user = sysUserService.selectUserDetail(id);

        Result result = ResultSelect.Select(user);

        return result;
    }

    @PostMapping("/spitChangeGold")
    public Result spitChangeGold(@RequestBody Map<String,Object> map){

     return sysUserService.SpitChangeGold(Integer.valueOf(map.get("cid").toString()),Long.valueOf(map.get("uid").toString()) );

    }

    @PostMapping("/commentInsertGold")
    public int CommentInSertGold(@RequestBody Gold gold){
        return sysUserService.CommentInsertGold(gold);
    }

    @GetMapping("/getSomeDetail/{id}")
    public Result getSomeDetail(@PathVariable Long id){

        SysUser user = sysUserService.selectAllUserDetail(id);

        if (user!=null){

            return  new Result(true,200,"查询成功",user);
        }else {
            return  new Result(false,409,"查询失败",user);
        }


    }


    @GetMapping("/getDetailPrice/{id}")
    public Result getDetailPrice(@PathVariable Long id){

        Float aFloat = sysUserService.userDetailPresent(id);

        return  new Result(true,200,"查询成功",aFloat);

    }

    @PostMapping("/userDetailUpdate")
    public Result userDetailUpdate(@RequestBody SysUser sysUser){

        int i = sysUserService.userDetailUpdate(sysUser);


        if (i>0){
            return  new Result(true,200,"更新成功");
        }else {
            return  new Result(false,409,"更新失败");
        }

    }

    @GetMapping("/userReplyTime/{id}")
    public void userReplyTime(@PathVariable Long id){
        sysUserService.updateReplyTime(id);
    }

    @PostMapping("/doEmailCheck")
    public Result doEmailCheck(@RequestBody MailUser mailUser){

        Result result = sysUserService.emailCheck(mailUser);

        return result;
    }

    @GetMapping("/active/**")
    public Result emailCodeCheck(HttpServletRequest request){

        String sp = "/doUser/active/";

        String uri = request.getRequestURI();

        String auth = uri.substring(sp.length());
        Result result = sysUserService.eCheckCode(auth);

        return result;
    }

    @GetMapping("/getChat/{uid}/{tid}")
    public Result getChatList(@PathVariable String uid,@PathVariable String tid){

        List<WkMsg> chatList = sysUserService.getChatList(uid, tid);

        return ResultSelect.Select(chatList);


    }

    @GetMapping("/getUserDetail/{id}")
    public SysUser getUserNameDetail(@PathVariable("id") long id){
        SysUser wkUser = userMapper.selectUser(id);

        return wkUser;

    }

    @PostMapping("/getCurientUserInfo")
    public Result getCurientUserInfo(HttpServletRequest request){
        String auth = request.getHeader("auth");
        String prefix = "";
        String token = "";
        if (!StringUtils.isEmpty(auth) && auth != "null" ){
            prefix = env.getProperty("jwt.config.prefix");
            token  = auth.substring(prefix.length());
            Claims claims = jwtUtil.parseJWT(token);
            Object role = claims.get("role");
            SysUser sysUser = JSON.parseObject(role.toString(), SysUser.class);
            SysUser wkUser = userMapper.selectUser(sysUser.getId());
            wkUser.setVoId(wkUser.getId().toString());
            if (wkUser != null){
                return new Result(true,200,"",wkUser);
            }else {
                return new Result(false,400,"未登录",null);
            }

        }else {
            return new Result(false,400,"未登录",null);
        }

    }

    @GetMapping("/doFriendRequest/{id}/{uid}")
    public Result addFriendRequest(@PathVariable("id") Long id,@PathVariable("uid") Long uid){

        Map<String,Object> map = new HashMap<>();

        SysUser sysUser = userMapper.selectUser(id);

        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (sysUser!=null){
            map.put("uName",sysUser.getUserName());
            map.put("uid",sysUser.getUid());
        }
        map.put("msg","发来了一条好友请求");
        map.put("format",format);
        map.put("upid",uid);


        Result result = sysUserService.addFirendRequest(id, uid,map);
        return result;


    }

    @GetMapping("/checkFriendRequest/{id}/{uid}")
    public Result checkFriendRequest(@PathVariable("id") Long id,@PathVariable("uid") Long uid){

        SysUser sysUser = userMapper.selectUser(uid);
        Map<String,Object> map = new HashMap<>();

        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (sysUser!=null){
            map.put("uName",sysUser.getUserName());
            map.put("uid",sysUser.getUid());

        }
        map.put("msg","添加好友成功，对方已同意");
        map.put("format",format);
        map.put("upid",uid);

        Result result = sysUserService.checKFriendRequest(id, uid,map);
        return result;

    }

    @GetMapping("/getFriendDetailList/{id}")
    public Result getFriendDetailList(@PathVariable("id") Long id){

        List<WKUser> friendList = sysUserService.getFriendList(id);

        return ResultSelect.Select(friendList);

    }


}
