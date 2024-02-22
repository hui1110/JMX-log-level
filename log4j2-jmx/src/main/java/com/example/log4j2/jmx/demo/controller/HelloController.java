package com.example.log4j2.jmx.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final static Logger log = LogManager.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public String hello(){
        log.debug("lo4j2 jmx debug message !");
        return "Hello world.";
    }
}
