package com.heroes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.configs.SocketHandler;
import com.heroes.configs.WebSocketConfig;
import com.heroes.model.AlarmEvent;
import com.heroes.model.CamImage;
import com.heroes.repository.AlarmEventRepository;
import com.heroes.repository.CamRepository;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.StandardWatchEventKinds;

@SpringBootApplication
public class SupermanApplication {

    private static final Logger log = LoggerFactory.getLogger(SupermanApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(SupermanApplication.class, args);
    }


    @Autowired
    CamRepository camRepository;

    @Autowired
    SocketHandler socketHandler;


    @Bean
    public CommandLineRunner commandLineRunner(AlarmEventRepository repository) {
        return args -> {


            System.out.println("Let's inspect the beans provided by Spring Boot:");

            //define a folder root
            Path myDir = Paths.get("target/classes/static/public");


//            try {
//                WatchService watcher = myDir.getFileSystem().newWatchService();
//                myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
//                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
//
//                WatchKey watckKey = watcher.take();
//
//                List<WatchEvent<?>> events = watckKey.pollEvents();
//                for (WatchEvent event : events) {
//                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
//                        System.out.println("Created: " + event.context().toString());
//                    }
//                    if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
//                        System.out.println("Delete: " + event.context().toString());
//                    }
//                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
//                        System.out.println("Modify: " + event.context().toString());
//                    }
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.toString());
//            }


            //check folder
//            Path path= Paths.get("target/classes/static/public");
//            final List<Path> files=new ArrayList<>();
//            try {
//                Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
//                    @Override
//                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                        if(!attrs.isDirectory()){
//                            files.add(file);
//                        }
//                        return FileVisitResult.CONTINUE;
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            log.info(Arrays.toString(files.toArray()));

//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}

//            String test = "{ \"magnetSensor\": 1, \"pirSensor\": 0}";
//            ObjectMapper mapper = new ObjectMapper();
//            AlarmEvent got = mapper.readValue(test, AlarmEvent.class);
//            repository.save(got);
//            log.info(got.toString());
//            repository.save(got);
//            repository.save(new AlarmEvent("test1", "test2"));
//            repository.save(new AlarmEvent("1", "0"));
//            repository.save(new AlarmEvent("1", "0"));
//            repository.save(new AlarmEvent("1", "1"));

//            log.info("-------------------------------");
//            for (AlarmEvent alarm : repository.findAll()) {
//                log.info(alarm.toString());
//            }
//            log.info("");

//            AlarmEvent ae = new AlarmEvent("lamp", "1");
//            String json =mapper.writeValueAsString(ae);
//            log.info(json);
//
//            AlarmEvent aea = mapper.readValue(test, AlarmEvent.class);
//            log.info(aea.toString());


        };
    }

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("localhost");
        sf.setPort(500);
        sf.setUsername("spring");
        sf.setPassword("pass");
        return new CachingSessionFactory<FTPFile>(sf);
    }

    @Bean
    public IntegrationFlow ftpInboundFlow() {
        return IntegrationFlows
                .from(s -> s.ftp(this.ftpSessionFactory())
                                .preserveTimestamp(true)
                                .remoteDirectory("")
                                .regexFilter(".*\\.jpg$")
                                .localDirectory(new File("target/classes/static/public")),

                        e -> e.id("ftpInboundAdapter")
                                .autoStartup(true)
                                .poller(Pollers.fixedDelay(5000)))
                .handle(m -> {
                    System.out.println(m.getPayload());
                    socketHandler.addFtpstuff(m.getPayload().toString());

                })
                .get();
    }

//    end of class
}
