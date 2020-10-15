package com.hepsiburada.client;

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
    private String readMessage;

    public RabbitMQClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    }

    public String getReadMessage() {
        return readMessage;
    }

    public void publishBrand(String writeMessage) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, writeMessage.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + writeMessage + "'");

    }

    public void basicConsume() throws IOException {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            readMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + readMessage + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

}
