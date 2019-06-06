package com.cin.socket2serial;

import com.cin.socket2serial.util.IOUtil;

import java.io.IOException;
import java.io.OutputStream;
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
        CommPort commPort = portIdentifier.open(portName, 2222);
        if (commPort instanceof ParallelPort) {
            ParallelPort parallelPort = (ParallelPort) commPort;
            System.out.println("开启并口成功，并口名称：" + portName);
            return parallelPort;
        }
        throw new NoSuchPortException();
    }

    @Override
    public void close(CommPort commPort) {
        if (commPort != null) {
            commPort.close();
            System.out.println("关闭了并口：" + commPort.getName());
        }
    }

    @Override
    public void sendData(CommPort commPort, byte[] data) {
        OutputStream os = null;
        try {
            os = commPort.getOutputStream();//获得串口的输出流
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(os);
        }
    }
}
