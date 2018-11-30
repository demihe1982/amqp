/**
 * @author edayahe
 * @create 2018-11-29 4:20 PM
 **/
package cn.dyan.queue;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author edayahe
 * @create 2018-11-29 4:20 PM
 **/
public class AmqpReceiver implements Runnable {
   QueueingConsumer consumer;

    public AmqpReceiver() {
        String boostrapServers = "localhost:5672";
        String queueName = "hello.world.queue";
        boolean autoAck = true;
        String consumerTag = "myConsumer";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection(getAddrs(boostrapServers));
            channel = connection.createChannel();

            consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, consumer);




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           /* try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
*/
        }

    }

    private List<Address> getAddrs(String boostrapServers){
        final List<Address> addressList = new ArrayList<>();
        Arrays.asList(boostrapServers.split(",")).stream().forEach((s)->{
            String[] str = s.split(":");
            addressList.add(new Address(str[0],Integer.valueOf(str[1])));
        });

        return addressList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Receive message is : " + new String(consumer.nextDelivery().getBody()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}