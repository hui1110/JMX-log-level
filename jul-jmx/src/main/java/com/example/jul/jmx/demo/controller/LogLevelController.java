package com.example.jul.jmx.demo.controller;

import com.example.jul.jmx.demo.configuration.LoggerMBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

@RestController
public class LogLevelController {

    @RequestMapping("/updateLogLevel")
    public String updateLogLevel(@RequestParam String loggerName, @RequestParam String level) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        Logger logger = Logger.getLogger(loggerName);

        DynamicMBean mbean = new LoggerMBean(logger);

        try {
            ObjectName objectName = new ObjectName(String.format("java.util.logging:type=Logger,name=%s", loggerName));
            if(!mbs.isRegistered(objectName)){
                mbs.registerMBean(mbean, objectName);
                System.out.println("register MBean");
            }
            mbs.invoke(objectName, "setLevel", new Object[]{level}, new String[]{String.class.getName()});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "ok";
    }

}
