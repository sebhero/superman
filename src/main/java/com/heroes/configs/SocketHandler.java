package com.heroes.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.model.AlarmEvent;
import com.heroes.repository.AlarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sebastian Boreback on 2017-01-17.
 */
public class SocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);

    List<WebSocketSession> sessions= new CopyOnWriteArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    AlarmRepository repository;


    ArrayList<AlarmEvent> alarmEvents = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("got: "+message.getPayload());
        log.info("sedning message to sessions");


        AlarmEvent got = mapper.readValue(message.getPayload(), AlarmEvent.class);
//        log.info("converted data");
//        log.info("gotObj: "+got.toString());
        alarmEvents.add(got);
//        for (AlarmEvent alarmEvent : alarmEvents) {
//            log.info("in db: "+alarmEvent.toString());
//        }


        log.info("db: "+alarmEvents.toString());


        for (WebSocketSession webSocketSession : sessions){
//            for (AlarmEvent alarmEvent : db.getAlarmEvents()) {
//                log.info("sending: "+alarmEvent+" to: "+webSocketSession.toString());
//                webSocketSession.sendMessage(new TextMessage(alarmEvent.toString()));
//            }
            webSocketSession.sendMessage(new TextMessage("hello"+message.getPayload()+"!"));
//            for (AlarmEvent alarmEvent : alarmEvents) {
//                log.info("sending: "+alarmEvent.toString());
//                webSocketSession.sendMessage(new TextMessage(alarmEvent.toString()+"!"));
//            }

//            String value = message.getPayload();
//            db.getAlarmEvents().forEach(alarmEvent -> {
//
//                try {
//                    log.info("sending: "+alarmEvent.toString());
//                    webSocketSession.sendMessage(new TextMessage("hello"+alarmEvent.toString()+"!"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            });


        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("add new ws connection "+session.getId());
        //add new connections to list of sessions
        sessions.add(session);
    }

//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        super.afterConnectionClosed(session, status);
//        sessions.remove(session);
//
//        log.info("connection closed: "+session.toString());
//    }
}
