package com.heroes;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

import java.io.File;

@SpringBootApplication
public class SupermanApplication {

    private static final Logger log = LoggerFactory.getLogger(SupermanApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SupermanApplication.class, args);
    }

//    @Autowired
//    SocketHandler socketHandler;

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
//                    System.out.println(m.getPayload());
//                    socketHandler.addFtpstuff(m.getPayload().toString());

                })
                .get();
    }

}
