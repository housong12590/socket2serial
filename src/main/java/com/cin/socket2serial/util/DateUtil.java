package com.cin.socket2serial.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {

    private static DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    public static DateFormat getDefaultFormat() {
        return DEFAULT_FORMAT;
    }
}
