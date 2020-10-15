package com.hepsiburada.steps;

import com.hepsiburada.client.RabbitMQClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;


public class RabbitMQSteps {
    public String writeMessage;
    public String readMessage;
    RabbitMQClient rabbitMQClient;
    public RabbitMQSteps(RabbitMQClient rabbitMQClient) {
        this.rabbitMQClient = rabbitMQClient;
    }

    @Given("^Create new Message \"([^\"]*)\"$")
    public void createNewMessage(String writeMessage) throws Throwable {
        rabbitMQClient.publishBrand(writeMessage);
    }

    @When("^Reading in created message$")
    public void readingInMessage() throws Exception {
        rabbitMQClient.basicConsume();
    }

    @Then("^Check the message read with the typed message$")
    public void checkTheMessageReadWithTheTypedMessage() throws Exception {
        Thread.sleep(10000);
        Assert.assertEquals(writeMessage,readMessage);
    }


}



