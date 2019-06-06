package com.cin.socket2serial;

import java.util.Map;

import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

public interface Port {

    CommPort open(String portName, Map<String, Object> options) throws PortInUseException, NoSuchPortException;

    void close(CommPort commPort);

    void sendData(CommPort commPort, byte[] data);
}
