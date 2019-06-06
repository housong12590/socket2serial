package com.cin.socket2serial.service;

import com.cin.socket2serial.Application;
import com.cin.socket2serial.ParallelPortImpl;
import com.cin.socket2serial.Port;
import com.cin.socket2serial.SerialPortImpl;
import com.cin.socket2serial.util.LogUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

public class PrinterService implements Runnable {

    private LinkedList<byte[]> taskQueue = new LinkedList<>();
    private CommPort commPort;
    private Port port;
    private static final int closeTime = 30 * 1000;
    private long sTime = 0;

    public PrinterService() {
        String mode = Application.getProperty().getMode();
        if ("parallel".equals(mode)) {
            this.port = new ParallelPortImpl();
        } else {
            this.port = new SerialPortImpl();
        }
    }

    public void start() {
        new Thread(this).start();
    }


    private void openCommPort() {
        port.close(commPort);
        String portName = Application.getProperty().getPortName();
        try {
            Map<String, Object> options = new HashMap<>();
            if (port instanceof SerialPortImpl) {
                int baudRate = Application.getProperty().getBaudRate();
                options.put("baudRate", baudRate);
            }
            commPort = port.open(portName, options);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            LogUtil.error(String.format("没有找到%s端口", portName));
        } catch (PortInUseException e) {
            e.printStackTrace();
            LogUtil.error(String.format("%s 端口正在被使用", portName));
        }
    }

    public void reload() {
        openCommPort();
    }

    public void add(byte[] data) {
        taskQueue.add(data);
    }


    @Override
    public void run() {
        while (true) {
            if (taskQueue.isEmpty()) {
                long nowTime = System.currentTimeMillis();
                if (nowTime - sTime > closeTime) {
                    closeCommPort();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                byte[] data = taskQueue.removeFirst();
                print(data);
                sTime = System.currentTimeMillis();
            }
        }
    }

    private void print(byte[] data) {
        if (commPort == null) {
            openCommPort();
        }
        if (commPort != null) {
            port.send(commPort, data, 512);
        }
    }

    private void closeCommPort() {
        if (commPort != null) {
            port.close(commPort);
            commPort = null;
        }
    }
}
