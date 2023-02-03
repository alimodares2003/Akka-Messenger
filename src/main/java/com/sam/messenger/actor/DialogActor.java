package com.sam.messenger.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.io.Tcp;
import akka.util.ByteString;
import com.sam.messenger.SerializeUtils;
import com.sam.messenger.events.ReceivedMessageEvent;
import com.sam.messenger.events.SendMessageEvent;
import com.sam.messenger.models.TextMessage;
import org.w3c.dom.Text;
import scala.collection.generic.CanBuildFrom;

public class DialogActor extends AbstractActor {

    private final Long authenticatedUser;

    public DialogActor(Long authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public static Props props(Long userId) {
        return Props.create(DialogActor.class, userId);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Tcp.Received.class, msg -> {
                    Object message = SerializeUtils.deserialize(msg.data().toArray());
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received message: " + textMessage.getValue() + " " + getSender().hashCode());
                    } else
                        System.out.println("Received message: " + msg.data().utf8String());
                })
//                .match(SendMessageEvent.class, event -> {
////                    authenticatedUser++;
//                    System.out.println(authenticatedUser);
//                    //check authenticated user can perform this action(can send message)
//                    //find dialog by id
//                    // if authenticate user is in dialog members and dialog is Private send message to another user
//                    //save message in database
////                    Long secondUserId = 2L;
//                    getContext().actorOf(DialogActor.props(event.getMessage().getDialogId()))
//                            .tell(ReceivedMessageEvent.create(event.getMessage()), getSelf());
//                })
//                .match(ReceivedMessageEvent.class, event -> {
//                    if (event.getMessage() instanceof TextMessage) {
//                        TextMessage textMessage = (TextMessage) event.getMessage();
//                        System.out.println("Received message from " + event.getMessage().getDialogId() + " : " + textMessage.getValue());
//                    }
//                })
                .build();
    }
}
