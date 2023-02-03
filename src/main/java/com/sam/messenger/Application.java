package com.sam.messenger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.sam.messenger.actor.ServerActor;
//import com.sam.messenger.zarin.ServerActor;

public class Application {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ServerActorSystem");

//        ActorRef sender = system.actorOf(DialogActor.props(1L), "dialogActor");
//        sender.tell("Hello World!", null);
//        sender.tell(SendMessageEvent.create(new TextMessage("Hello Ali", 2L)), sender);


        ActorRef serverActor = system.actorOf(ServerActor.props(null), "serverActor");
    }
}
