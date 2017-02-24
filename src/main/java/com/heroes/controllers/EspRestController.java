package com.heroes.controllers;

import com.heroes.configs.SocketHandler;
import com.heroes.model.AlarmEvent;
import com.heroes.repository.AlarmEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by Sebastian Boreback on 2017-01-31.
 */
//REST controller for input from esp8266
@RestController
public class EspRestController {

    private static final Logger log = LoggerFactory.getLogger(EspRestController.class);

    @Autowired
    private
    SocketHandler sh;

    @Autowired
    private
    AlarmEventRepository alarmEventRepository;

    //handles alarm events from esp8266
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ralarm", method = RequestMethod.GET)
    public void greeting(@RequestParam(value = "event") String data) {
        AlarmEvent got = null;
//        log.info("REST event:[" + data + "]");
        //save to DB
        switch (data) {
            case "PIR":

                try {
                    got = this.alarmEventRepository.save(new AlarmEvent("0", "1"));
                    log.info("saved: " + got.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "MAGNET":

                try {
                    got = this.alarmEventRepository.save(new AlarmEvent("1", "0"));
                    log.info("saved: " + got.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                got = null;
                break;
        }

        //send to websocket lcients
        if (sh != null) {
            if (sh.sessions.size() > 0) {
                for (WebSocketSession session : sh.sessions) {
                    try {
                        log.info("send to websocket");
                        if (session != null) {
                            if (got != null) {
                                session.sendMessage(new TextMessage(got.toString()));

                            } else {
                                log.info("got is null");
                            }
                        } else {
                            log.info("session is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
