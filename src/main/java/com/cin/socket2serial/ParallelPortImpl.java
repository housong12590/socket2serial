package com.cin.socket2serial;

import java.util.Map;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.ParallelPort;
import gnu.io.PortInUseException;

public class ParallelPortImpl implements Port {

    @Override
    public CommPort open(String portName, Map<String, Object> options) throws PortInUseException, NoSuchPortException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        CommPort commPort = portIdentifier.open(portName, 5000);
        if (commPort instanceof ParallelPort) {
            ParallelPort parallelPort = (ParallelPort) commPort;
            System.out.println(String.format("开启端口: %s", commPort.getName()));
            return parallelPort;
        }
        throw new NoSuchPortException();
    }
}
