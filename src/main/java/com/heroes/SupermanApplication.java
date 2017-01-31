package com.heroes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.configs.TempDB;
import com.heroes.model.AlarmEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SupermanApplication {

    private static final Logger log = LoggerFactory.getLogger(SupermanApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(SupermanApplication.class, args);
    }


    @Autowired
    TempDB db;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}

            String test = "{ \"magnetSensor\": 1, \"pirSensor\": 0}";
            ObjectMapper mapper = new ObjectMapper();
            AlarmEvent got = mapper.readValue(test, AlarmEvent.class);
//            repository.save(got);
            log.info(got.toString());
            db.getAlarmEvents().add(got);
//            AlarmEvent ae = new AlarmEvent("lamp", "1");
//            String json =mapper.writeValueAsString(ae);
//            log.info(json);
//
//            AlarmEvent aea = mapper.readValue(test, AlarmEvent.class);
//            log.info(aea.toString());


        };
    }
}
