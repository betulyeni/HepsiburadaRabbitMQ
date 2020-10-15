package com.hepsiburada.steps;

import com.rabbitmq.client.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class RabbitMQSteps {
    public final static String QUEUE_NAME = "hello";
    public String writeMessage;
    public String readMessage;

    RabbitMQClient rabbitMQClient;

    public RabbitMQSteps(RabbitMQClient rabbitMQClient) {
        this.rabbitMQClient = rabbitMQClient;
    }

    @Given("^Create new Message \"([^\"]*)\"$")
    public void createNewMessage(String arg0) throws Throwable {
        writeMessage = arg0;
        rabbitMQClient.publishBrand(writeMessage);
        System.out.println(" [x] Sent '" + writeMessage + "'");

    }

    @When("^Reading in created message$")
    public void readingInMessage() throws Exception {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        rabbitMQClient.basicConsume();

    }

    @Then("^Check the message read with the typed message$")
    public void checkTheMessageReadWithTheTypedMessage() throws Exception {
        Thread.sleep(10000);
        assertEquals(writeMessage, readMessage);

    }


}



