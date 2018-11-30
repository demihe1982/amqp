/**
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
package cn.dyan;

import cn.dyan.amqp.AmqpDirectMessageContainer;
import cn.dyan.amqp.AmqpSimpleMessageContainer;

/**
 *
 * @author edayahe
 * @create 2018-11-30 12:40 PM
 **/
public class MutilContainer {

    public static void main(String[] args){

        AmqpSimpleMessageContainer container1 = new AmqpSimpleMessageContainer();
        AmqpSimpleMessageContainer container2 = new AmqpSimpleMessageContainer();

        AmqpDirectMessageContainer container3 = new AmqpDirectMessageContainer();
        AmqpDirectMessageContainer container4 = new AmqpDirectMessageContainer();
//        container3.start();
//        container4.start();

        container1.start();
        container2.start();
    }
}