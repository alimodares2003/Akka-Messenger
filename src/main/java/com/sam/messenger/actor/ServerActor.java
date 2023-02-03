package com.sam.messenger.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

public class ServerActor extends AbstractActor {

    private ActorRef manager;

    public ServerActor(ActorRef manager) {
        this.manager = manager;
    }

    public static Props props(ActorRef manager) {
        return Props.create(ServerActor.class, manager);
    }

    @Override
    public void preStart() throws Exception {
        if (manager == null) {
            manager = Tcp.get(getContext().getSystem()).manager();
        }
        manager.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("localhost", 2282), 100), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Tcp.Bound.class,
                        msg -> {
                            System.out.println("Server Bounded");
//                            manager.tell(msg, getSelf());
                        })
                .match(
                        Tcp.CommandFailed.class,
                        msg -> {
                            System.out.println("CommandFailed");
                            getContext().stop(getSelf());
                        })
                .match(
                        Tcp.Connected.class,
                        conn -> {
//                            manager.tell(conn, getSelf());
                            System.out.println("Connected and register actors " + conn.productPrefix());

                            final ActorRef handler =
                                    getContext().actorOf(DialogActor.props(1L));
                            getSender().tell(TcpMessage.register(handler), getSelf());
                        })
                .build();
    }
}
