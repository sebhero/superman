package com.heroes.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.model.AlarmEvent;
import com.heroes.model.AlarmJson;
import com.heroes.repository.AlarmEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sebastian Boreback on 2017-01-17.
 */
@Component
public class SocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);


    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public SocketHandler(AlarmEventRepository repository) {
        this.repository = repository;
    }


    private AlarmEventRepository repository;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("got: " + message.getPayload());


        //try catch..
        AlarmJson got = new AlarmJson();
        try {
            got = mapper.readValue(message.getPayload(), AlarmJson.class);
            log.info("got is: " + got.toString());


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            repository.save(new AlarmEvent(got.getMagnetSensor(), got.getPirSensor()));
        } catch (Exception e) {
            log.error("save to db");
            log.error(e.getMessage());
            e.printStackTrace();
        }

        //return data
        for (WebSocketSession webSocketSession : sessions) {

            webSocketSession.sendMessage(new TextMessage("hello" + message.getPayload() + "!"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("add new ws connection " + session.getId());
        //add new connections to list of sessions
        sessions.add(session);
        //todo extra send db data
        log.info("try to send data");
        for (AlarmEvent event : repository.findAll()) {
            try {

                session.sendMessage(new TextMessage(event.toString()));
            } catch (IOException e) {
                log.error("after connection");
                log.error(e.getMessage());
            }
        }


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
        sessions.remove(session);

        log.info("connection closed: " + session.toString());
    }
}
