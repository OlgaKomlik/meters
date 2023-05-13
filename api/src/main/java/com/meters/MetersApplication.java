package com.meters;


import com.meters.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.meters")
/*@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@Import(
        {ApplicationConfig.class}
)*/
/*@EnableCaching
@EnableTransactionManagement*/
public class MetersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetersApplication.class, args);
    }

}
