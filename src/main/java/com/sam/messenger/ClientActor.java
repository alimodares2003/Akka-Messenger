package com.sam.messenger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;

import java.net.InetSocketAddress;

public class ClientActor extends AbstractActor {

    final InetSocketAddress remote;
    final ActorRef listener;

    public static Props props(InetSocketAddress remote, ActorRef listener) {
        return Props.create(ClientActor.class, remote, listener);
    }

    public ClientActor(InetSocketAddress remote, ActorRef listener) {
        this.remote = remote;
        this.listener = listener;

        if (listener == null) {
            listener = Tcp.get(getContext().getSystem()).manager();
        }
        listener.tell(TcpMessage.connect(remote), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Tcp.CommandFailed.class,
                        msg -> {
                            System.out.println("CommandFailed");
//                            listener.tell("failed", getSelf());
                            getContext().stop(getSelf());
                        })
                .match(
                        Tcp.Connected.class,
                        msg -> {
//                            listener.tell(msg, getSelf());
                            getSender().tell(TcpMessage.register(getSelf()), getSelf());
                            getContext().become(connected(getSender()));
                            System.out.println("Connected to Server");

//                            getSender().tell(TcpMessage.write(ByteString.fromArray("hello2".getBytes())), getSelf());

                        })
                .build();
    }

    private Receive connected(final ActorRef connection) {
        return receiveBuilder()
                .match(
                        ByteString.class,
                        msg -> {
                            System.out.println("Message sending");
                            connection.tell(TcpMessage.write((ByteString) msg), getSelf());
                        })
                .match(
                        Tcp.CommandFailed.class,
                        msg -> {
                            System.out.println("CommandFailed");
                            // OS kernel socket buffer was full
                        })
                .match(
                        Tcp.Received.class,
                        msg -> {
                            System.out.println("new message receive");
//                            listener.tell(msg.data(), getSelf());
                        })
                .matchEquals(
                        "close",
                        msg -> {
                            System.out.println("close");
                            connection.tell(TcpMessage.close(), getSelf());
                        })
                .match(
                        Tcp.ConnectionClosed.class,
                        msg -> {
                            System.out.println("ConnectionClosed");
                            getContext().stop(getSelf());
                        })
                .build();
    }
}
