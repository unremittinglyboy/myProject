package cn.lsr.noveladmin.config;

import cn.lsr.noveladmin.constants.mqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbitmq配置类
 */

@Configuration
public class mqConfig {
    /**
     * exclusive属性的队列只对首次声明它的连接可见，并且在连接断开时自动删除
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(mqConstants.BOOK_EXCHANGE, true, false);
    }

    @Bean
    public Queue insertQueue(){
        return new Queue(mqConstants.BOOK_INSERT_QUEUE, true);
    }

    @Bean
    public Queue deleteQueue(){
        return new Queue(mqConstants.BOOK_DELETE_QUEUE, true);
    }

    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(mqConstants.BOOK_INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(mqConstants.BOOK_DELETE_KEY);
    }
}
