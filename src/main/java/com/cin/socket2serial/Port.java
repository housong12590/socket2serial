package com.cin.socket2serial;

import com.cin.socket2serial.util.IOUtil;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

public interface Port {

    CommPort open(String portName, Map<String, Object> options) throws PortInUseException, NoSuchPortException;

    default void close(CommPort commPort) {
        if (commPort != null) {
            commPort.close();
            System.out.println(String.format("关闭端口: %s", commPort.getName()));
        }
    }

    default void send(CommPort commPort, byte[] data, int bufferSize) {
        BufferedOutputStream bos = null;
        try {
            OutputStream os = commPort.getOutputStream();
            bos = new BufferedOutputStream(os, bufferSize);
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(bos);
        }
    }
}
