Feature: RabbitMQ Message
  Scenario: Sending and Reading in Message
    Given Create new Message "Rabbitmq öğreniyorum"
    When  Reading in created message
    Then Check the message read with the typed message





