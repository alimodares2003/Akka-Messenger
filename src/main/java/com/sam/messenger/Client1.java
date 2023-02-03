package com.sam.messenger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.io.TcpMessage;
import akka.util.ByteString;
import com.sam.messenger.models.TextMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        ActorSystem system = ActorSystem.create("ClientActorSystem");

//        ActorRef sender = system.actorOf(DialogActor.props(1L), "dialogActor");
//        sender.tell("Hello World!", null);
//        sender.tell(SendMessageEvent.create(new TextMessage("Hello Ali", 2L)), sender);


        final ActorRef client = system.actorOf(ClientActor.props(
                new InetSocketAddress("localhost", 2282),
                null), "client1");

//        client.tell(TcpMessage.write(ByteString.fromArray("hello22".getBytes())),null);

        Thread.sleep(2000);
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");

//        client.tell(ByteString.fromString("Ack user 1 to server"), null);
        while (true) {
            String text = myObj.nextLine();
            TextMessage textMessage = new TextMessage(text,2L);

            client.tell(ByteString.fromArray(SerializeUtils.serialize(textMessage)), null);
//            client.tell(ByteString.fromString(text), null);
        }
//        client.tell("hello2",client);
    }
}
