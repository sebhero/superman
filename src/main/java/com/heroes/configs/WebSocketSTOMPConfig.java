//package com.heroes.configs;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//
///**
// * Created by Sebastian Boreback on 2017-02-10.
// */
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketSTOMPConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/gs-guide-websocket").withSockJS();
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        super.configureClientInboundChannel(registration);
//
//    }
//}
