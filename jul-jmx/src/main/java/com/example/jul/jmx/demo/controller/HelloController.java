package com.example.jul.jmx.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HelloController {

    private final static Logger logger = Logger.getLogger(HelloController.class.getName());

    @RequestMapping("/hello")
    public String hello(){
        logger.fine("Fine message !");
        return "Hello";
    }
}
