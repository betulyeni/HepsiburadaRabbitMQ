package com.hepsiburada.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.util.concurrent.TimeoutException;

public class RabbitMQClient {

    private static String EXCHANGE_NAME = "fanout-exchange";
    private static String QUEUE_NAME_1 = "fanout-queue-1";
    private static String QUEUE_NAME_2 = "fanout-queue-2";
    private static String QUEUE_NAME_3 = "fanout-queue-3";
    private static String ROUTING_KEY = "";
    private final Channel channel;
    private  String queue_readMessage1;
    private  String queue_readMessage2;
    private  String queue_readMessage3;



    public RabbitMQClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);
        channel.queueDeclare(QUEUE_NAME_3, false, false, false, null);

    }

    public String getQueue_readMessage1() {
        return queue_readMessage1;
    }
    public String getQueue_readMessage2() {
        return queue_readMessage2;
    }
    public String getQueue_readMessage3() {
        return queue_readMessage3;
    }

    public void publishBasic(String writeMessage) throws IOException {
        channel.basicPublish("", QUEUE_NAME_1, null, writeMessage.getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("", QUEUE_NAME_2, null, writeMessage.getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("", QUEUE_NAME_3, null, writeMessage.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + writeMessage + "'");

    }

    public void basicConsume_One() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            queue_readMessage1 = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Queue_One '" + queue_readMessage1 + "'");
        };
        channel.basicConsume(QUEUE_NAME_1, true, deliverCallback, consumerTag -> {
        });

    }
    public void basicConsume_Two() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            queue_readMessage2 = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Queue_Two '" + queue_readMessage2 + "'");
        };
        channel.basicConsume(QUEUE_NAME_2, true, deliverCallback, consumerTag -> {
        });
    }
    public void basicConsume_Three() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            queue_readMessage3 = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Queue_Three '" + queue_readMessage3 + "'");
        };
        channel.basicConsume(QUEUE_NAME_3, true, deliverCallback, consumerTag -> {
        });
    }


}

