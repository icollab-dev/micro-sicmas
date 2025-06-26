package com.mx.microsicmas;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableFeignClients("com.dgc.microsicmas.client")
public class MicroSicmasApplication {

    @Autowired
    ObjectMapper  objectMapper;


    @PostConstruct
    public void init() {
        log.info("Spring boot app running in timezone: " + new Date());
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
        TimeZone.setDefault(tz);
        objectMapper.setTimeZone(TimeZone.getDefault());
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroSicmasApplication.class, args);
    }
}
