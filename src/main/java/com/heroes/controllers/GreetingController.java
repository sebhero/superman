//package com.heroes.controllers;
//
//import com.heroes.model.Greeting;
//import com.heroes.model.HelloMessage;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
///**
// * Created by Sebastian Boreback on 2017-01-17.
// */
//
//@Controller
//public class GreetingController {
//
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(HelloMessage message) throws Exception
//    {
//        Thread.sleep(1000);
//        return new Greeting(1,"Hello, "+message.getName()+"!");
//    }
//
//}
