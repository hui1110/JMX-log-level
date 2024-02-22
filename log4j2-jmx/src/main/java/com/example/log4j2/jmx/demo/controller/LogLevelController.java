package com.example.log4j2.jmx.demo.controller;

import com.example.log4j2.jmx.demo.configuration.LoggerConfigMBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@RestController
public class LogLevelController {

    @RequestMapping("/updateLogLevel")
    public String updateLogLevel(@RequestParam String loggerName, @RequestParam String level){
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            Logger logger = (Logger) LogManager.getLogger(loggerName);
            ObjectName objectName = new ObjectName(String.format("org.apache.logging.log4j2:type=LoggerContext,name=%s", loggerName));
            DynamicMBean mbean = new LoggerConfigMBean(logger);
            if(!mbs.isRegistered(objectName)) {
                mbs.registerMBean(mbean, objectName);
                System.out.println("register MBean");
            }
            mbs.invoke(objectName, "setLevel", new Object[]{level}, new String[]{String.class.getName()});
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
        return "OK";
    }

}
