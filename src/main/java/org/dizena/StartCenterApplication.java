package org.dizena;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class StartCenterApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication application = new SpringApplication(StartCenterApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(4096));
        ConfigurableApplicationContext context = application.run(args);
        Environment env = context.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String active = env.getProperty("spring.profiles.active");
        String name = env.getProperty("spring.application.name");
        if ("prod".equals(active)) {
            log.info("Application " + name + " started! Access URLs:\n\t" +
                    "Local   : \thttp://localhost:" + port + "\n\t"
            );
        } else {
            log.info("Application " + name + " started! Access URLs:\n\t" +
                    "Local   : \thttp://localhost:" + port + "\n\t" +
                    "External: \thttp://" + ip + ":" + port + "\n\t" +
                    "Swagger : \thttp://" + ip + ":" + port + "/doc.html\n\t" +
                    "mvn tar : \tmvn clean package -Ptest|prod pwd-qq123321"
            );
        }
    }

}
