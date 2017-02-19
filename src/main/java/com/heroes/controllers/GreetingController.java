package com.heroes.controllers;

import com.heroes.model.AlarmEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Sebastian Boreback on 2017-01-17.
 */

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception
    {
        Thread.sleep(1000);
        return "Hello from websocket:"+message.toString();
    }



}
