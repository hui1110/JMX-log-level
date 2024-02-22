## Use JMX to update the log level of a running application demo

This demo shows how to use JMX to update the log level of a running application.

**Note**:

1. In logback, JMX registers MBeans through the _JMXConfigurator_ to change the logging level. According to this [article](https://logback.qos.ch/news.html#1.3.0), _JMXConfigurator_ has been removed since 1.3.0. When using _JMXConfigurator_, the version of _slf4j-api_ should be 1.7.x or earlier.

2. If you want to use the jconsole client that comes with the JDK to view MBeans, Spring Boot > 2.2.x disables JMX exposure by default, and you need to add the following settings to make it work:

    ```properties
    spring.jmx.enabled=true
    server.tomcat.mbeanregistry.enabled=true
    ```
3. _DynamicMBean_ needs to be implemented in **jul-jmx** and **log4j2-jmx** demo, otherwise an error will be reported.

### Test

#### jul-jmx

1. Send a request to change the log level of a running application using JMX.

    ```bash
    curl http://localhost:8080/updateLogLevel?loggerName=com.example.jul.jmx.demo.controller.HelloController&level=FINE
    ```
2. Check whether FINE level logs are output to the console.

    ```bash
    curl http://localhost:8080/hello
    ```

#### log4j2-jmx

1. Send a request to change the log level of a running application using JMX.

    ```bash
    curl http://localhost:8081/updateLogLevel?loggerName=com.example.log4j2.jmx.demo.controller.HelloController&level=DEBUG
    ```
   
2. Check whether DEBUG level logs are output to the console.

    ```bash
    curl http://localhost:8081/hello
    ```

#### logback-jmx

1. Send a request to change the log level of a running application using JMX.

    ```bash
    curl http://localhost:8082/updateLogLevel?loggerName=com.example.logback.jmx.demo.controller.HelloController&level=DEBUG
    ```
2. Check whether DEBUG level logs are output to the console.

    ```bash
    curl http://localhost:8082/hello
    ```