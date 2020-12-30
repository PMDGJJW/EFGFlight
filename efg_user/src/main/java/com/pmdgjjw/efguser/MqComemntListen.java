package com.pmdgjjw.efguser;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/8/19 16:14
 * @Description
 */
//@Component
public class MqComemntListen {


//    @RabbitListener(queues ="efg.comment" )
//    public String getComment(String s){
//
//        System.out.println("efg.comment接收到消息：");
//       return s;
//
//    }
//
//    @RabbitListener(queues ="efg.reply" )
//    public void getReply(String s){
//
//        System.out.println("efg.reply接收到消息：");
//        System.out.println("手机号："+s);
//
//    }


}
