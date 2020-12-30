package com.pmdgjjw.efguser;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmdgjjw.efguser.entity.SysUser;
import com.pmdgjjw.efguser.entity.WxMsg;
import com.pmdgjjw.efguser.entity.WxReplyMsg;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @auth jian j w
 * @date 2020/8/19 9:49
 * @Description
 */

@ServerEndpoint("/webSocket/{uid}")
@Component
public class WebSockerServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static Map<String, WebSockerServer> webSocketSet = new ConcurrentHashMap<>();

    private static Map<String, Connection> mqConn = new ConcurrentHashMap<>();

    private static Map<String, Connection> mqConnrpl = new ConcurrentHashMap<>();

    private static Map<String, Channel> mqChenllrpl = new ConcurrentHashMap<>();

    private static Map<String, Channel> mqChenll = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static RedisTemplate redisTemplate;



    @Autowired
    public void getRedisTemplate(RedisTemplate redisTemplate){
        WebSockerServer.redisTemplate = redisTemplate;
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("uid") String uid){
        this.session = session;

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


        String QUEUE_NAME = "efg.comment";
        String QUEUE_NAME_REPLY = "efg.reply";
        String EXCHACGE_NAME = "efgTopicExchange";
        try {
            //打开连接和创建频道，与发送端一样
            ConnectionFactory factory = new ConnectionFactory();
            //设置MabbitMQ所在主机ip或者主机名
            factory.setHost("192.168.0.11");

            Connection connection = factory.newConnection();

            if(mqConn.containsKey(uid)){
                mqConn.remove(uid);
                mqConn.put(uid,connection);
                //加入set中
            }else{
                mqConn.put(uid,connection);
            }

            Channel channel = connection.createChannel();

            if(mqChenll.containsKey(uid)){
                mqChenll.remove(uid);
                mqChenll.put(uid,channel);
                //加入set中
            }else{
                mqChenll.put(uid,channel);
            }

            channel.queueBind(QUEUE_NAME,EXCHACGE_NAME,"efg.comment");


            //创建队列消费者
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);

                    String string = getObjString(body);

                    WxMsg wxMsg = JSON.parseObject(string, WxMsg.class);

                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    wxMsg.setDate(format);

                    Object o = wxMsg.getUpid();

                    String wsuid= o.toString();


                    string = JSON.toJSONString(wxMsg);

                    if (webSocketSet.containsKey(wsuid) ){

                        if (doRedisSaveMsg(uid, string)){
                            channel.basicAck(envelope.getDeliveryTag(), true);

                        }

                        try {
                            webSocketSet.get(wsuid).sendMessage(string);
                                        //关闭连接
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else {
                        channel.basicNack(envelope.getDeliveryTag(), false,true);
                    }

                }


            };

           channel.basicConsume(QUEUE_NAME, false, consumer);


            //指定消费队列


            Connection connectionrlp = factory.newConnection();

            if(mqConnrpl.containsKey(uid)){
                mqConnrpl.remove(uid);
                mqConnrpl.put(uid,connection);
                //加入set中
            }else{
                mqConnrpl.put(uid,connection);
            }

            Channel channelReply = connectionrlp.createChannel();

            if(mqChenllrpl.containsKey(uid)){
                mqChenllrpl.remove(uid);
                mqChenllrpl.put(uid,channel);
                //加入set中
            }else{
                mqChenllrpl.put(uid,channel);
            }

            channelReply.queueBind(QUEUE_NAME_REPLY,EXCHACGE_NAME,"efg.reply");

            DefaultConsumer consumerReply = new DefaultConsumer(channelReply){

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);


                    String objString = getObjString(body);

                    WxReplyMsg wxReplyMsg = JSON.parseObject(objString, WxReplyMsg.class);
                    Object o = wxReplyMsg.getUserid();

                    String o1 = o.toString();

                    objString = JSON.toJSONString(wxReplyMsg);

                        if (webSocketSet.containsKey(o1) ){

                            try {
                                webSocketSet.get(o1).sendMessage(objString);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (doRedisSaveMsg(uid, objString) ){
                            channelReply.basicAck(envelope.getDeliveryTag(), true);

                            }else {
                                channel.basicNack(envelope.getDeliveryTag(), false,true);
                            }
                        }

                }
            };


            channelReply.basicConsume(QUEUE_NAME_REPLY, false, consumerReply);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("uid") String uid){


        webSocketSet.remove(uid);  //从set中删除
        try {
            if(mqChenll.containsKey(uid) && mqChenll.get(uid).isOpen()){
                mqChenll.get(uid).close();
                mqChenll.remove(uid);
            }

           if (mqConn.containsKey(uid) && mqConn.get(uid).isOpen())
            mqConn.get(uid).close();
            mqConn.remove(uid);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {

            if (mqChenllrpl.containsKey(uid) && mqChenllrpl.get(uid).isOpen()){
                mqChenllrpl.get(uid).close();
                mqChenllrpl.remove(uid);
            }

          if (mqConnrpl.containsKey(uid) && mqConnrpl.get(uid).isOpen()){
              mqConnrpl.get(uid).close();
              mqConnrpl.remove(uid);
          }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (TimeoutException e) {
            e.printStackTrace();
        }



    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {


        Set<Map.Entry<String,WebSockerServer>> set = webSocketSet.entrySet();


        //群发消息
        for(Map.Entry<String, WebSockerServer> item: set){

                try {
                    item.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error ,@PathParam("uid") String uid){

        webSocketSet.remove(uid);  //从set中删除
        try {
            if(mqChenll.containsKey(uid) && mqChenll.get(uid).isOpen()){
                mqChenll.get(uid).close();
                mqChenll.remove(uid);
            }

            if (mqConn.containsKey(uid) && mqConn.get(uid).isOpen())
                mqConn.get(uid).close();
            mqConn.remove(uid);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {

            if (mqChenllrpl.containsKey(uid) && mqChenllrpl.get(uid).isOpen()){
                mqChenllrpl.get(uid).close();
                mqChenllrpl.remove(uid);
            }

            if (mqConnrpl.containsKey(uid) && mqConnrpl.get(uid).isOpen()){
                mqConnrpl.get(uid).close();
                mqConnrpl.remove(uid);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (TimeoutException e) {
            e.printStackTrace();
        }

        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
//        this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendText(message);
    }

    public  Boolean doRedisSaveMsg(String uid,String objString){

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        Boolean add = redisTemplate.opsForZSet().add("wx::uid" + uid, objString, new Date().getTime());

        return add;
    }

    public String getObjString(byte[] bytes){
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String string = JSON.toJSONString(obj);

        return string;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSockerServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSockerServer.onlineCount--;
    }


}
