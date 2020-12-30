package com.pmdgjjw.efguser.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pmdgjjw.efguser.dao.GoldCheckMapper;
import com.pmdgjjw.efguser.dao.GoldMappper;
import com.pmdgjjw.efguser.dao.UserGoldMapper;
import com.pmdgjjw.efguser.dao.UserMapper;
import com.pmdgjjw.efguser.entity.*;
import com.pmdgjjw.efguser.service.DateCompare;
import com.pmdgjjw.efguser.service.UserService;
import com.pmdgjjw.efguser.util.CheckCode;
import com.pmdgjjw.efguser.util.EmailUtil;
import com.pmdgjjw.entity.Result;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.pmdgjjw.util.IPUtils;
import com.pmdgjjw.util.IdWorker;
import com.pmdgjjw.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @auth jian j w
 * @date 2020/6/28 22:18
 * @Description
 */
@Service
@Transactional
public class SysUserServiceImpl extends BaseImpl<SysUser> implements UserService {


    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoldCheckMapper goldCheckMapper;

    @Autowired
    private GoldMappper goldMappper;

    @Autowired
    private UserGoldMapper userGoldMapper;

    @Autowired
    private IPUtils ipUtils;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Environment env;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public Integer getCode(MailUser mailUser) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        Map<String,Object> map =  new HashMap<>();
        String code = CheckCode.getCode();

        map.put("name",mailUser.getName());
        map.put("code",code);

         emailUtil.sendMimeMessge(map, "/mail/mail.ftl", "注册验证码", mailUser.getEmail());

        Object o = redisTemplate.opsForValue().get("name:" + mailUser.getName() + ":email:" + mailUser.getEmail());

        if ( o == null){
            redisTemplate.opsForValue().set("name:"+mailUser.getName()+":email:"+mailUser.getEmail(),code,15, TimeUnit.MINUTES);
        }

        return 1;
    }

    @Override
    public int doMailCodeCheck(SysUser sysUser,String mailCode) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        String name = sysUser.getName();

        String email = sysUser.getEmail();


        String code = (String) redisTemplate.opsForValue().get("name:" + name + ":email:" + email);

        if ( !StringUtils.isEmpty(code) && mailCode.equals(code)){
            return 1;
        }
        else {
            return 2;

        }

    }

    @Override
    public int doRegiest(SysUser sysUser, String mailCode, HttpServletRequest request) {

        SysUser user = userMapper.selectOne(sysUser);

        String ipDetail = ipUtils.getClientAddress(request);

        sysUser.setRegiestTime(new Date());

        sysUser.setRegisIp(ipDetail);

        sysUser.setUid( idWorker.nextId());

        sysUser.setId( idWorker.nextId());

        sysUser.setUserType(1);

        String passWord = sysUser.getPassword();

        sysUser.setPassword(encoder.encode(passWord));

        sysUser.setEmailCheck(0);

        sysUser.setTelCheck(1);

        sysUser.setDelFlag(0);

        sysUser.setUserType(1);

        int i = 0;

        if (user == null){
            i =  userMapper.insertSelective(sysUser);
        }else {
            return 3;
        }
        return i;
    }


    @Override
    public Map<String,Object> doLogin(String name , String passWord) {

        SysUser user = new SysUser();

        user.setName(name);

        user = userMapper.selectOne(user);


        String checkPsd = user.getPassword();

        boolean matches = encoder.matches(passWord, checkPsd);


        Map<String,Object> map = new HashMap<>();

        if (matches){
           user.setLoginTime(new Date());

           userMapper.updateByPrimaryKeySelective(user);


            user.setPassword(null);

            SysUser pld = new SysUser();
            pld.setId(user.getId());
            pld.setName(user.getName());
            pld.setUserType(user.getUserType());

            String string = JSON.toJSONString(pld);


            String token = jwtUtil.createJWT(String.valueOf(user.getId()), user.getUserName(), string);
            map.put("username",user.getUserName());
            map.put("userId",user.getId());
            map.put("userType",user.getUserType());
            map.put("userUid",user.getUid());
            map.put("headPic",user.getHeadPortrait());
            map.put("token",token);
            return map;
        }else {
            return null;
        }

    }

    @Override
    public SysUser selectUserDetail(Long id) {

        SysUser user = userMapper.selectUserDetail(id);



        return user;
    }

    @Override
    public Result SpitChangeGold(Integer cid, Long uid) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        int flag = 0;

        Gold gold = new Gold();

        GoldCheck goldCheck = new GoldCheck();
        goldCheck.setCid(cid);
        goldCheck.setUid(uid);

        if (redisTemplate.opsForHash().get("goldTack", cid + "::" + uid) == null && goldCheckMapper.selectOne(goldCheck) == null) {

            gold.setCid(cid);
            gold = goldMappper.selectOne(gold);

            if (gold != null && gold.getGcount() > 0) {

                UserGold userGold = new UserGold();
                userGold.setUid(Long.valueOf(uid));
                userGold = userGoldMapper.selectOne(userGold);

                int i = 10;

                if (userGold != null) {
                    i = userGold.getGprice() + gold.getGsize();
                } else {
                   flag=0;
                }

                if (userGold != null) {
                    flag = userGoldMapper.updateByUid(Long.valueOf(userGold.getUid()), i);
                }




                if (flag > 0) {

                    redisTemplate.opsForHash().put("goldTack", cid + "::" + uid, 1);
                    flag = goldMappper.updateByCount(gold.getId(), gold.getGcount() - 1);

                }

            } else {
                flag = 2;
            }

        } else {

            flag = 2;
        }


        if (flag>0 && flag!=2){

            return new Result(true,200,"评论成功,金币加"+gold.getGsize(),"1");
        }else if (flag>0 && flag==2){
            return new Result(true,200,"评论成功","2");
        }
        else {
            return new Result(true,200,"评论失败，请联系塔台","0");
        }


    }

    @Override
    public int CommentInsertGold(Gold gold) {

        int insert = goldMappper.insert(gold);

        return insert;
    }

    @Override
    public SysUser selectAllUserDetail(Long id) {
        SysUser user = new SysUser();
        user.setUid(id);
        user = userMapper.selectOne(user);

        if (user !=null){

            user.setPassword(null);
        }

        return user;
    }

    @Override
    public Float userDetailPresent(Long uid) {
        SysUser user = new SysUser();

        int i = 0 ;
        float d = 0;
        user.setUid(uid);
        user = userMapper.selectOne(user);

        if (user!=null){

        if (StringUtils.isEmpty(user.getUserName())){
            i+=1;
        }else if (StringUtils.isEmpty(user.getTel())){
            i+=1;
        }else if (StringUtils.isEmpty(user.getAddress())){
            i+=1;
        }else if (user.getSex()==null){
            i+=1;
        }else if (StringUtils.isEmpty(user.getFavouritePlane())){
            i+=1;
        }else if (StringUtils.isEmpty(user.getHeadPortrait())){
            i+=1;
        }else if (user.getBirthday()==null){
            i+=1;
        }

        d = Math.round(i/7.0*100);
        }

        d=100-d;

        return d;
    }

    @Override
    public int userDetailUpdate(SysUser sysUser) {

        if (sysUser !=null){
            sysUser.setDelFlag(null);
            sysUser.setUid(null);
            sysUser.setPassword(null);
        }

        int i = userMapper.updateByPrimaryKeySelective(sysUser);
        return i;
    }

    @Override
    public void updateReplyTime(Long id) {

        SysUser sysUser = new SysUser();

        sysUser.setId(id);

        sysUser.setReplyTime(new Date());

        userMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public Result emailCheck(MailUser mailUser) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        Map<String,Object> map = new HashMap<>();

        String jwt = new String();

        String format = new SimpleDateFormat("MMddHHmmSS").format(new Date());

        if (mailUser !=null){
            map.put("name",mailUser.getName());
            map.put("domain","127.0.0.1:9003/user");
            jwt = jwtUtil.createJWT(mailUser.getName(), mailUser.getEmail(), format);

            map.put("token","EFGFlight_"+jwt);
        }


        emailUtil.sendMimeCheckMessge(map, "/mail/EmailCheck.ftl", "邮箱激活及验证", mailUser.getEmail());

        redisTemplate.opsForValue().set("mail:" + mailUser.getEmail() + ":role:" + format, jwt, 2, TimeUnit.HOURS);

        return new Result(true,200,"邮箱验证激活已发送，请注意查收") ;
    }

    @Override
    public Result eCheckCode(String auth) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        String prefix = env.getProperty("jwt.config.prefix");

        String token  = auth.substring(prefix.length());


        Claims claims = null;
        try {
            claims = jwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String email = claims.get("sub", String.class);
        String id = claims.get("jti", String.class);
        String role = claims.get("role", String.class);

        SysUser sysUser = new SysUser();

        sysUser.setName(id);
        sysUser.setEmail(email);

        SysUser sysUser1 = userMapper.selectOne(sysUser);

        if (sysUser1.getEmailCheck()==0){
            Object o = redisTemplate.opsForValue().get("mail:" + email + ":role:" + role);

            if (o ==null){
                return new Result(false,409,"邮箱激活链接期限已过期");
            }else {
                sysUser1.setEmailCheck(1);
                int i = userMapper.updateByPrimaryKeySelective(sysUser1);
                if (i>0){

                    redisTemplate.delete("mail:" + email + ":role:" + role);

                    return new Result(true,200,"邮箱激活成功");
                }else {
                    return new Result(false,409,"邮箱激活失败");
                }

            }
        }else {

            return new Result(true,200,"该邮箱已经激活");
        }

    }

    @Override
    public List<WkMsg> getChatList(String uid,String Tid) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Date date = new Date();
        long time = date.getTime();
        long timefar = time-86400000;

        Set<String> setTO = redisTemplate.opsForZSet().reverseRangeByScore(uid + "::" + Tid, timefar, time);
        Set<String> setME = redisTemplate.opsForZSet().reverseRangeByScore(uid + "::" + uid, timefar, time);

        List<String> chatList = new ArrayList<>();

        chatList.addAll(setTO);
        chatList.addAll(setME);

        List<WkMsg> wkMsgList = new ArrayList<>();

        for (String wkMsg : chatList) {

            WkMsg wkMsg1 = JSON.parseObject(wkMsg, WkMsg.class);
            wkMsgList.add(wkMsg1);
        }

        Collections.sort(wkMsgList, new DateCompare());

        for (WkMsg wkMsg : wkMsgList) {
            Calendar calendar = Calendar.getInstance();

        String format = new SimpleDateFormat("HH:mm:ss").format(date);
        if(date !=null){
            calendar.setTime(date);
            if (Calendar.AM == calendar.get(Calendar.AM_PM)) {

                wkMsg.setDateFormat("上午"+format);
            }else {
                wkMsg.setDateFormat("下午"+format);
            }
        }
        }


        return wkMsgList;
    }


    @Override
    public Result addFirendRequest(Long id,Long fid,Map<String,Object> map) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Object o = redisTemplate.opsForValue().get("uid::" + id + "::friendsRequest");

        if (o != null){
            return new Result(false,409,"该好友请求已存在，请等待同意");

        }else {

            redisTemplate.opsForValue().set("uid::" + id + "::friendsRequest", fid,7,TimeUnit.DAYS);
            redisTemplate.opsForValue().set("uid::" + fid + "::friendsRequestGet", id,7,TimeUnit.DAYS);

            amqpTemplate.convertAndSend("efgTopicExchange", "efg.comment", map);
            return new Result(true,200,"好友添加已发送，请等待对方验证");
        }

    }

    @Override
    public Result checKFriendRequest(Long id, Long uid,Map<String,Object> map) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        Object o = redisTemplate.opsForValue().get("uid::" + id + "::friendsRequestGet");

        String rqid = o.toString();

        Long add = 0l;

        if (o !=null && rqid.equals(String.valueOf(uid))){
           add  = redisTemplate.opsForSet().add("uid::" + id + "::friend", uid);
           add  = redisTemplate.opsForSet().add("uid::" + uid + "::friend", id);
        }

        if (add != 0l){

            amqpTemplate.convertAndSend("efgTopicExchange", "efg.comment", map);
            return new Result(true,200,"好友请求已同意");
        }
        return new Result(false,409,"系统出错了");
    }

    @Override
    public List<WKUser>  getFriendList(Long id) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


        Set members = redisTemplate.opsForSet().members("uid::" + id + "::friend");


        List<WKUser> list = userMapper.FridenDetailList(members);


        return list;
    }
}
