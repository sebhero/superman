package com.heroes.configs;
/***
 * @author Sebastian Boreback
 */



import com.heroes.repository.AlarmEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//Config up the websocket
@CrossOrigin(maxAge = 3600, origins = "*")
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private AlarmEventRepository alarmEventRepository;

    @Autowired
    private
    SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(socketHandler, "/alarms").setAllowedOrigins("*");

    }
}
