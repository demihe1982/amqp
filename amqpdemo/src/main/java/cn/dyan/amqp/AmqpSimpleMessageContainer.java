/**
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
package cn.dyan.amqp;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 *
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
public class AmqpSimpleMessageContainer {
    SimpleMessageListenerContainer container = null;

    public AmqpSimpleMessageContainer(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setExclusive(true);
        container.setQueueNames("hello.world.queue");
        container.setMessageListener(new AmqpMessageReceiver());
    }

    public void start(){
        container.start();
    }

}