package com.honeywell.atmapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class AtmappApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmappApplication.class, args);
    }

}
