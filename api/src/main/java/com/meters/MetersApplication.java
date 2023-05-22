package com.meters;


import com.meters.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.meters")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@Import(
        {ApplicationConfig.class}
)
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class MetersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetersApplication.class, args);
    }

}
