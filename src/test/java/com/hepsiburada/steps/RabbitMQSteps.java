package com.hepsiburada.steps;

import com.hepsiburada.client.RabbitMQClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;


public class RabbitMQSteps {
    public String writeMessage;
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
    public void readingInMessage() throws Exception {
        rabbitMQClient.basicConsume();
        Thread.sleep(2000);
        this.readMessage=rabbitMQClient.getReadMessage();
    }

    @Then("^Check the message read with the typed message$")
    public void checkTheMessageReadWithTheTypedMessage() throws Exception {
        Thread.sleep(5000);
        Assert.assertEquals(writeMessage,readMessage);
    }


}



