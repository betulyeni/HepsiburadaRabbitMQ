package com.hepsiburada.steps;

import com.hepsiburada.client.RabbitMQClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;


public class RabbitMQSteps {
    private String writeMessage;
    private String queue_readMessage1;
    private String queue_readMessage2;
    private String queue_readMessage3;
    RabbitMQClient rabbitMQClient;
    public RabbitMQSteps(RabbitMQClient rabbitMQClient) {
        this.rabbitMQClient = rabbitMQClient;
    }


    @Given("^I send the message \"([^\"]*)\"$")
    public void createNewMessage(String writeMessage) throws Throwable {
        this.writeMessage=writeMessage;
        rabbitMQClient.publishBasic(writeMessage);
    }

    @When("^I consume the Test_One queue queue$")
    public void iConsumeTheTest_OneQueueQueue() throws  IOException,InterruptedException{
        rabbitMQClient.basicConsume_One();
        Thread.sleep(2000);
        this.queue_readMessage1 =rabbitMQClient.getQueue_readMessage1();
    }

    @When("^I consume the Test_Two queue queue$")
    public void iConsumeTheTest_TwoQueueQueue() throws  IOException,InterruptedException{
        rabbitMQClient.basicConsume_Two();
        Thread.sleep(2000);
        this.queue_readMessage2 =rabbitMQClient.getQueue_readMessage2();
    }

    @When("^I consume the Test_Three queue queue$")
    public void iConsumeTheTest_ThreeQueueQueue() throws  IOException,InterruptedException {
        rabbitMQClient.basicConsume_Three();
        Thread.sleep(2000);
        this.queue_readMessage3 =rabbitMQClient.getQueue_readMessage3();
    }


    @Then("^I expect the sent and received message are same$")
    public void checkTheMessageReadWithTheTypedMessage() {
        Assert.assertEquals(writeMessage, queue_readMessage1);
        Assert.assertEquals(writeMessage, queue_readMessage2);
        Assert.assertEquals(writeMessage, queue_readMessage3);
    }


}



