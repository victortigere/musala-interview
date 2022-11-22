package com.musala.drone;

import com.musala.drone.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DroneApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }
}
