package com.example.logback.jmx.demo.controller;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jmx.JMXConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@RestController
public class LogLevelController {

    @RequestMapping("/updateLogLevel")
    public String updateLogLevel(@RequestParam String loggerName, @RequestParam String level){
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            ObjectName objectName = new ObjectName("ch.qos.logback.classic:Name=default,Type=ch.qos.logback.classic.jmx.JMXConfigurator");
            JMXConfigurator configurator = new JMXConfigurator(lc, mbs, objectName);
            if(!mbs.isRegistered(objectName)) {
                mbs.registerMBean(configurator, objectName);
                System.out.println("register MBean");
            }
            configurator.setLoggerLevel(loggerName, level);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
        return "OK";
    }

}
