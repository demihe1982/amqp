/**
 * @author edayahe
 * @create 2018-11-29 4:22 PM
 **/
package cn.dyan;

import cn.dyan.queue.AmqpReceiver;

/**
 *
 * @author edayahe
 * @create 2018-11-29 4:22 PM
 **/
public class MultipThread {

    public static void main(String[] args){

        Thread consumer1 = new Thread(new AmqpReceiver());
        Thread consumer2 = new Thread(new AmqpReceiver());

        consumer1.start();
//        consumer2.start();



    }
}