//package com.heroes.controllers;
//
//import com.heroes.model.Greeting;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * Created by trevl on 2017-01-17.
// */
//@RestController
//public class GreetingRestController {
//
//    private static final Logger log = LoggerFactory.getLogger(GreetingRestController.class);
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        log.info("REST event");
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }
//
//    @CrossOrigin(origins = "*")
//    @RequestMapping("axis-cgi/com/ptz.cgi")
//    public String camCom(@RequestParam (value = "pan", defaultValue="world") String pan) {
//        log.info("got REST req: "+pan);
//        return "Pan: "+pan;
//    }
//}
