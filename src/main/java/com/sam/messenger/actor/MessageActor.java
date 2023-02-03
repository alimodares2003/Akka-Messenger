package com.sam.messenger.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class MessageActor extends AbstractActor {

    public static Props props() {
        return Props.create(MessageActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> {  // Receive a message from a user
                    System.out.println("Received message: " + message); // Print the received message

                    // Send the received message to another user
                    getContext().actorSelection("/user/receiver").tell(message, getSelf());

                })
                .build();
    }
}
