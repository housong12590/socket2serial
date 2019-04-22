package com.cin.socket2serial.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {


    public static void close(Closeable... closeable) {
        for (Closeable c : closeable) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
