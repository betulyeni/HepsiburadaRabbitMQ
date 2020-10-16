package com.hepsiburada.steps;

import com.hepsiburada.client.RabbitMQClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;


public class RabbitMQSteps {
    private String writeMessage;
    private String readMessage;
    RabbitMQClient rabbitMQClient;
    public RabbitMQSteps(RabbitMQClient rabbitMQClient) {
        this.rabbitMQClient = rabbitMQClient;
    }


    @Given("^Create new Message \"([^\"]*)\"$")
    public void createNewMessage(String writeMessage) throws Throwable {
        this.writeMessage=writeMessage;
        rabbitMQClient.publishBrand(writeMessage);
    }

    @When("^Reading in created message$")
    public void readingInMessage() throws IOException,InterruptedException {
        rabbitMQClient.basicConsume();
        Thread.sleep(2000);
        this.readMessage=rabbitMQClient.getReadMessage();
    }

    @Then("^Check the message read with the typed message$")
    public void checkTheMessageReadWithTheTypedMessage() {
        Assert.assertEquals(writeMessage,readMessage);
    }


}



