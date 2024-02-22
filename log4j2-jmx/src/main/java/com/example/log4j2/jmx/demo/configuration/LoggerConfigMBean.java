package com.example.log4j2.jmx.demo.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import java.util.Arrays;

public class LoggerConfigMBean implements DynamicMBean {
    private final Logger logger;

    public LoggerConfigMBean(Logger logger) {
        this.logger = logger;
    }
    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException {
        if ("setLevel".equals(attribute)) {
            return logger.getLevel().name();
        }
        throw new AttributeNotFoundException("Unknown attribute: " + attribute);
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException {
        if ("setLevel".equals(attribute.getName())) {
            String level;
            Object value = attribute.getValue();
            if (value instanceof Object[]) {
                level = Arrays.toString((Object[]) value);
                level = level.substring(1);
                level = level.substring(0, level.length()-1);
            } else {
                level = value.toString();
            }
            logger.setLevel(Level.getLevel(level));
        } else {
            throw new AttributeNotFoundException("Unknown attribute: " + attribute.getName());
        }
    }
    @Override
    public AttributeList getAttributes(String[] attributes) {
        AttributeList list = new AttributeList();
        for (String attribute : attributes) {
            try {
                Object value = getAttribute(attribute);
                list.add(new Attribute(attribute, value));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        for (Object attribute : attributes) {
            try {
                setAttribute((Attribute) attribute);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return getAttributes(attributes.asList().stream().map(Attribute::getName).toArray(String[]::new));
    }
    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) {
        Attribute attribute = new Attribute(actionName, params);
        try {
            setAttribute(attribute);
        } catch (AttributeNotFoundException e) {
            throw new RuntimeException(e);
        }
        return attribute;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] attributes = new MBeanAttributeInfo[] {
                new MBeanAttributeInfo(
                        "Level",
                        "java.lang.String",
                        "The log level of the logger",
                        true,
                        true,
                        false
                )
        };

        return new MBeanInfo(
                this.getClass().getName(),
                "Dynamic MBean that wraps a LoggerConfig",
                attributes,
                null,
                null,
                null
        );
    }

}
