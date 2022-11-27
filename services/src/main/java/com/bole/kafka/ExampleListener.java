package com.bole.kafka;

/**
 * kafka-streams requires at least one listener
 */
//@KafkaListener(groupId = "ExampleListener")
public class ExampleListener {

//    @Topic("example")
    void example() {
        System.out.println("example");
    }
}
