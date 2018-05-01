package com.cepheis.spring.log;

import ch.qos.logback.ext.spring.web.WebLogbackConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class LogbackListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext sc = event.getServletContext();
        //添加系统属性示例代码
        if (org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS) {
            System.setProperty("log.path", "${CATALINA_HOME}" + File.separator + "logs");
        } else {//linux
            System.setProperty("log.path", "/logs");
        }
//        if (isProductEnv) {
//            System.setProperty("log.root.level", "INFO");
//        } else {//非生产环境
//            System.setProperty("log.root.level", "DEBUG");
//        }
        WebLogbackConfigurer.initLogging(sc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        WebLogbackConfigurer.shutdownLogging(event.getServletContext());
    }
}