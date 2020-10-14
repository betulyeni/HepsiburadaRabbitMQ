package com.hepsiburada.steps;
import com.rabbitmq.client.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.assertEquals;

public class RabbitMQSteps {
    private final static String QUEUE_NAME = "hello";
    public String writeMessage;
    public String readMessage;
    public Channel channel;


    public void setUp() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    }
    @Given("^Create new Message$")
    public void createNewMessage() throws Exception{
        setUp();
        writeMessage = "hello";
        channel.basicPublish("", QUEUE_NAME, null, writeMessage.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + writeMessage + "'");
    }

    @When("^Reading in created message$")
    public void readingInMessage() throws Exception {
        setUp();
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            readMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + readMessage + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }

    @Then("^Check the message read with the typed message$")
    public void checkTheMessageReadWithTheTypedMessage() throws Exception {
        Thread.sleep(2000);
        assertEquals(writeMessage, readMessage);

    }



}



