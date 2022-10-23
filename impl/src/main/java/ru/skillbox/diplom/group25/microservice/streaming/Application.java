package ru.skillbox.diplom.group25.microservice.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import ru.skillbox.diplom.group25.library.core.annotation.EnableOpenFeign;
import ru.skillbox.diplom.group25.library.core.annotation.EnableSecurity;

/**
 * Application
 *
 * @author alex90bar
 */


@EnableOpenFeign
@EnableSecurity
//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication //отключение Security через exclude
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
