package com.pmdgjjw.efguser;



import com.alibaba.fastjson.JSON;
import com.pmdgjjw.efguser.dao.UserMapper;
import com.pmdgjjw.efguser.entity.HttpSessionConfigurator;
import com.pmdgjjw.efguser.entity.WKUser;
import com.pmdgjjw.efguser.entity.WkMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auth jian j w
 * @date 2020/8/19 9:49
 * @Description
 */

@ServerEndpoint(value = "/chat/{uid}",configurator = HttpSessionConfigurator.class)
@Component
public class WebSockerTalkServer {


    //  这里使用静态，让 service 属于类
    private static UserMapper userMapper;

    private static RedisTemplate redisTemplate;


    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(UserMapper uMapper) {
        WebSockerTalkServer.userMapper = uMapper;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        WebSockerTalkServer.redisTemplate = redisTemplate;
    }


    private Integer chatFlag = 0;



    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSockerTalkServer> webSocketSet = new ConcurrentHashMap<>();

    private static Map<String, WKUser> webSocketUser = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("uid") String uid){
        this.session = session;


        WKUser wkUser = userMapper.selectUserWK(Long.valueOf(uid));

        if (wkUser != null){
            webSocketUser.put(uid,wkUser);
        }

        if(webSocketSet.containsKey(uid)){
            webSocketSet.remove(uid);
            webSocketSet.put(uid,this);
            //加入set中
        }else{
            webSocketSet.put(uid,this);
            //加入set中

            //在线数加1
        }
        addOnlineCount();


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("uid") String uid){

        webSocketSet.remove(uid);  //从set中删除
        subOnlineCount();           //在线数减1

    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("uid") String uid) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        String[] split = getMessageArrar(message);

        String ToID =  split[0];
        String msg =  split[1];


        try {

            WebSockerTalkServer webSockerTalkServer = webSocketSet.get(ToID);

            if (webSockerTalkServer !=null && !ToID .equals(uid) ){
                webSockerTalkServer.sendMessage(uid,split);
                changeChatFlagFalse();
            }else {
                changeChatFlagTrue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Map.Entry<String, WebSockerTalkServer>> entries = webSocketSet.entrySet();
        Date date = new Date();

        if (chatFlag ==1 && webSocketSet.containsKey(ToID)){



            Boolean add = redisTemplate.opsForZSet().add(uid+"::"+ToID , msg, date.getTime());

        }else if (chatFlag !=2 && !webSocketSet.containsKey(ToID)){

            WkMsg wkMsg = new WkMsg();
            wkMsg.setMsg(msg);
            wkMsg.setUid(ToID);
            wkMsg.setDate(date);
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

            redisTemplate.opsForZSet().add(uid + "::" + ToID, JSON.toJSONString(wkMsg), date.getTime());
            redisTemplate.opsForZSet().add(ToID + "::" + uid, JSON.toJSONString(wkMsg), date.getTime());

        }


    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){

        error.printStackTrace();

    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @throws IOException
     */
    public void sendMessage(String uid,String [] split){
        String ToID =  split[0];
        String msg =  split[1];

        WkMsg wkMsg = new WkMsg();
        wkMsg.setMsg(msg);
        wkMsg.setUid(ToID);
        Date date = new Date();

        wkMsg.setDate(date);
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

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());




        Boolean add = redisTemplate.opsForZSet().add(uid + "::" + ToID, JSON.toJSONString(wkMsg), date.getTime());

        try {
                if ( add = true && webSocketSet.containsKey(ToID) ){
                    this.session.getAsyncRemote().sendText(JSON.toJSONString(wkMsg));
                }else {
                    redisTemplate.opsForZSet().add(uid + "::" + ToID, JSON.toJSONString(wkMsg), date.getTime());
                    redisTemplate.opsForZSet().add(ToID + "::" + uid, JSON.toJSONString(wkMsg), date.getTime());
                }
            } catch (Exception e) {
            redisTemplate.opsForZSet().add(uid + "::" + ToID, JSON.toJSONString(wkMsg), date.getTime());
            redisTemplate.opsForZSet().add(ToID + "::" + uid, JSON.toJSONString(wkMsg), date.getTime());
            }



        //this.session.getAsyncRemote().sendText(message);
    }

    public synchronized void changeChatFlagTrue(){

        chatFlag = 1;

    }

    public synchronized void changeChatFlagFalse(){

        chatFlag = 2;

    }

    public synchronized String[] getMessageArrar(String message){

        String[] split = new String[2];
        if (!StringUtils.isEmpty(message)){
            split = message.split("::");
        }

        return split;
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSockerTalkServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSockerTalkServer.onlineCount--;
    }


}
