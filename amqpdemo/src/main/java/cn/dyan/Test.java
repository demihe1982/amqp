/**
 * @author edayahe
 * @create 2018-11-29 2:59 PM
 **/
package cn.dyan;

import cn.dyan.queue.QueueingConsumer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author edayahe
 * @create 2018-11-29 2:59 PM
 **/
public class Test {

    public static void main(String[] args){

        String boostrapServers="localhost:5672";
        String queueName="hello.world.queue";
        boolean autoAck=true;
        String consumerTag ="myConsumer";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        Connection connection = null;
       Channel channel =null;
        try {
     connection = factory.newConnection(getAddrs(boostrapServers));
     channel = connection.createChannel();
    //channel.exchangeDeclare(exchangeName,"direct",true);
    //channel.queueBind(queueName,"","");

    /*consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            //super.handleDelivery(consumerTag, envelope, properties, body);

            System.out.println("Receive message is : " + new String(body));

            this.getChannel().basicAck(envelope.getDeliveryTag(), autoAck);
        }
    };*/

    QueueingConsumer   consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName,consumer);

    while (true){
        System.out.println("Receive message is : " + new String(consumer.nextDelivery().getBody()));

    }


}catch (Exception e){
    e.printStackTrace();
}
finally {
    try {
        connection.close();
    }catch (Exception ex){
        ex.printStackTrace();
    }

}




    }


    private static List<Address> getAddrs(String boostrapServers){
        final List<Address> addressList = new ArrayList<>();
        Arrays.asList(boostrapServers.split(",")).stream().forEach((s)->{
            String[] str = s.split(":");
            addressList.add(new Address(str[0],Integer.valueOf(str[1])));
        });

        return addressList;
    }
}