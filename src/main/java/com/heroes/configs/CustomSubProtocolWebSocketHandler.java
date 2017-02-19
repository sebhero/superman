//package com.heroes.configs;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;
//
///**
// * Created by Sebastian Boreback on 2017-02-10.
// */
//public class CustomSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSubProtocolWebSocketHandler.class);
//
//    @Autowired
//    private SessionHandler sessionHandler;
//
//    public CustomSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
//        super(clientInboundChannel, clientOutboundChannel);
//    }
//
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        LOGGER.info("New websocket connection was established");
//        sessionHandler.register(session);
//        super.afterConnectionEstablished(session);
//    }
//}
