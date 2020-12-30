package com.pmdgjjw.efgflight.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auth jian j w
 * @date 2020/8/19 15:45
 * @Description
 */
@Configuration
public class RabbitMqConfig {

    @Bean("commentList")
    public Queue getCommentList(){
        return new Queue("efg.comment");
    }

    @Bean("reply")
    public Queue getReply(){
        return  new Queue("efg.reply");
    }

    @Bean("efgTopicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange("efgTopicExchange");
    }

    @Bean
    Binding bindingExchangeAndQueuemsg(@Qualifier("commentList") Queue queue, @Qualifier("efgTopicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("efg.comment");
    }

    @Bean
    Binding bindingWithList(@Qualifier("reply") Queue queue, @Qualifier("efgTopicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("efg.reply");
    }

}
