/**
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
package cn.dyan.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 *
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
public class AmqpDirectMessageContainer {
    DirectMessageListenerContainer container = null;

    public AmqpDirectMessageContainer(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        container = new DirectMessageListenerContainer(connectionFactory);
        container.setQueueNames("hello.world.queue");
        container.setConsumerTagStrategy((queue -> {return "test";}));
        container.setMessageListener(new AmqpMessageReceiver());
    }

    public void start(){
        container.start();
    }

}