package com.cin.socket2serial.util;

public class ConverterUtils {

    public static int toInt(Object object, int defaultVal) {
        if (object == null) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static int toInt(Object object) {
        return toInt(object, 0);
    }

    public static float toFloat(Object object, float defaultVal) {
        if (object == null) {
            return defaultVal;
        }
        try {
            return Float.parseFloat(object.toString());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static float toFloat(Object obj) {
        return toFloat(obj, 0.0f);
    }

    public static double toDouble(Object object, double defaultVal) {
        if (object == null) {
            return defaultVal;
        }
        try {
            return Double.parseDouble(object.toString());
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static double toDouble(Object object) {
        return toDouble(object, 0.00d);
    }

    public static boolean toBool(Object object, boolean defaultVal) {
        if (object == null) {
            return defaultVal;
        }
        return Boolean.parseBoolean(object.toString());
    }
}
