/**
 * @author edayahe
 * @create 2018-11-30 9:54 AM
 **/
package cn.dyan.amqp;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author edayahe
 * @create 2018-11-30 9:54 AM
 **/
public class AmqpMessageReceiver implements ChannelAwareMessageListener {
    final  Timer timer;

    final BlockingQueue<AckDelivery> ackerQueue = new LinkedBlockingDeque<>();

    public AmqpMessageReceiver(){
        timer = new Timer("ackers");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    AckDelivery delivery = null;
                    try {
                        delivery = ackerQueue.take();
                        delivery.getChannel().basicAck(delivery.deliveryTag, false);
                        System.out.println("Thread-"+Thread.currentThread().getId()+",Acker  deliveryTag is {"+delivery.deliveryTag+"} ...........");
                    } catch (Exception e) {
                        System.out.println("Acker error ...........");
                        e.printStackTrace();
                    }
                }

            }
        }, 1000,1000);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Thread-"+Thread.currentThread().getId()+String.format(", Receiver message is :{%s},deliveryTag is {"+message.getMessageProperties()+"}",new String(message.getBody())));
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        ackerQueue.put(new AckDelivery(channel,message.getMessageProperties().getDeliveryTag()));

    }


    private class AckDelivery{
        private Channel channel;
        private long deliveryTag;
        public AckDelivery (Channel channel,long deliveryTag){
            this.channel = channel;
            this.deliveryTag = deliveryTag;
        }

        public Channel getChannel() {
            return channel;
        }

        public long getDeliveryTag() {
            return deliveryTag;
        }
    }
}