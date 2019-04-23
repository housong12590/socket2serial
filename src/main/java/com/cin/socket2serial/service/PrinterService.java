package com.cin.socket2serial.service;

import com.cin.socket2serial.Application;
import com.cin.socket2serial.SerialTool;
import com.cin.socket2serial.util.LogUtil;

import java.util.LinkedList;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class PrinterService implements Runnable, SerialPortEventListener {

    private LinkedList<byte[]> taskQueue = new LinkedList<>();
    private SerialPort serialPort;
    private SerialTool serialTool;
    private static final int closeTime = 30 * 1000;
    private long sTime = 0;

    public PrinterService() {
        serialTool = SerialTool.getSerialTool();
    }

    public void start() {
        new Thread(this).start();
    }


    private void openSerialPort() {
        closeSerialPort();
        String serialPortName = Application.getProperty().getSerialPort();
        int baudRate = Application.getProperty().getBaudRate();
        try {
            serialPort = serialTool.openSerialPort(serialPortName, baudRate);
            serialTool.setListenerToSerialPort(serialPort, this);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            LogUtil.error(String.format("没有找到%s端口", serialPortName));
        } catch (PortInUseException e) {
            e.printStackTrace();
            LogUtil.error(String.format("%s 端口正在被使用", serialPortName));
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        openSerialPort();
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
                    closeSerialPort();
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
        if (serialPort == null) {
            openSerialPort();
        }
        if (serialPort != null) {
            serialTool.sendData(serialPort, data);
        }
    }

    private void closeSerialPort() {
        if (serialPort != null) {
            serialTool.closeSerialPort(serialPort);
            serialPort = null;
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }
}
