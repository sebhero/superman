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
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by trevl on 2017-01-17.
 */
@RestController
public class GreetingRestController {

    private static final Logger log = LoggerFactory.getLogger(GreetingRestController.class);

    @Autowired
    SocketHandler sh;

    @Autowired
    AlarmEventRepository alarmEventRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/ralarm", method = RequestMethod.GET)
    public void greeting(@RequestParam(value = "event") String data) {
        AlarmEvent got=null;
        log.info("REST event:["+data+"]");
        if (data.equals("PIR")) {

            try {
                 got = this.alarmEventRepository.save(new AlarmEvent("0", "1"));
                log.info("saved: " + got.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (data.equals("MAGNET")) {

            try {
                got = this.alarmEventRepository.save(new AlarmEvent("1", "0"));
                log.info("saved: " + got.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            got = null;
        }

        List<Path> files = getImages();
        log.info(Arrays.toString(files.toArray()));

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



        //todo connect to sockethandler stuff
//        if (wc.socketHandler.sessions != null) {
//            if (wc.socketHandler.sessions.size() > 0) {
//                for (WebSocketSession session : wc.socketHandler.sessions) {
//                    try {
//                        log.info("send to websocket");
//                        if (session != null) {
//                            if (got != null) {
//                                session.sendMessage(new TextMessage(got.toString()));
//
//                            } else {
//                                log.info("got is null");
//                            }
//                        } else {
//                            log.info("session is null");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }

    }



    @CrossOrigin(origins = "*")
    @RequestMapping("testar")
    public String camCom(@RequestParam (value = "pan", defaultValue="world") String pan) {
        log.info("got REST req: "+pan);
        return "Pan: "+pan;
    }

    public List<Path> getImages() {
        Path path= Paths.get("target/classes/static/public");
        final List<Path> files=new ArrayList<>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(!attrs.isDirectory()){

                        files.add(file.getFileName());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        return files;
    }
}
