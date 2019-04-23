package com.cin.socket2serial.util;

import com.cin.socket2serial.Application;
import com.cin.socket2serial.ServiceProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    static Logger logger = LoggerFactory.getLogger("printservice");

    static boolean debug;

    static {
        ServiceProperty property = Application.getProperty();
        if (property != null) {
            debug = property.isDebug();
        }
    }

    public static void debug(String msg) {
        if (debug) {
            logger.debug(msg);
        }
    }

    public static void info(String msg) {
        if (debug) {
            logger.info(msg);
        }
    }

    public static void error(String msg) {
        if (debug) {
            logger.error(msg);
        }
    }

    public static void warn(String msg) {
        if (debug) {
            logger.warn(msg);
        }
    }

    public static void trace(String msg) {
        if (debug) {
            logger.trace(msg);
        }
    }
}
