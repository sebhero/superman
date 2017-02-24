package com.heroes.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.model.AlarmEvent;
import com.heroes.repository.AlarmEventRepository;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizingMessageSource;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sebastian Boreback on 2017-01-17.
 */

//Hanldes websocket communication
@Component
public class SocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);
    private final AlarmEventRepository alarmEventRepository;


    public List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    ObjectMapper mapper = new ObjectMapper();
    private ArrayList<String> pics = new ArrayList<>();


    @Autowired
    public SocketHandler(AlarmEventRepository alarmEventRepository) {
        this.alarmEventRepository = alarmEventRepository;
    }


    //handles messages from websocket clients
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        log.info("got: " + message.getPayload());

        sessions.forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message.getPayload()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //After client connected add new connection to list of connection
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("session: " + session.getAcceptedProtocol());
        log.info("add new ws connection " + session.getId());
        //add new connections to list of sessions
        sessions.add(session);

        //show pics saved on server
        if (pics.size() > 0) {
            pics.forEach(pic -> {
                try {
                    session.sendMessage(new TextMessage(pic));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
        //show list of events
        try {
            if (alarmEventRepository != null) {
                long count = alarmEventRepository.count();
                int page = (int) (count / 20);
                for (AlarmEvent alarmEvent : alarmEventRepository.findAll(new PageRequest(page, 20))) {
                    session.sendMessage(new TextMessage(alarmEvent.toString()));
                }
            } else
                log.info("alarm rep is null");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //after connection is lost remove client
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
        sessions.remove(session);

        log.info("connection closed: " + session.toString());
    }

    //Send ftp updates to client about new images
    public void sendFtpUpdate(String payload) {
        log.info("ws send from ftp");
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public void addFtpstuff(String picpath) {
//        String st1 = picpath.replace("\\", ",");
//        String[] str = st1.split(",");
//        String send = "";
//        for (String s : str) {
//            if (s.contains(".jpg")) {
//                log.info(s);
//                send = s;
//                for (WebSocketSession session : sessions) {
//                    try {
//                        session.sendMessage(new TextMessage(s));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        log.info("ftpgot: " + picpath);
//        this.pics.add(send);
//
//    }
    //end of class
}
