package com.hepsiburada.steps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQClient {
    public final static String QUEUE_NAME = "hello";
    public Channel channel;
    public Connection connection;
    public String readMessage;

    public RabbitMQClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    }

    public void publishBrand(String writeMessage) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, writeMessage.getBytes(StandardCharsets.UTF_8));
    }

    public void basicConsume() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            readMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + readMessage + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

}
