package com.cin.socket2serial;

import java.util.Map;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortImpl implements Port {
    @Override
    public CommPort open(String portName, Map<String, Object> options) throws PortInUseException, NoSuchPortException {
        int baudRate = Integer.parseInt(options.get("baudRate").toString());
        //通过端口名称得到端口
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        //打开端口，（自定义名字，打开超时时间）
        CommPort commPort = portIdentifier.open(portName, 2222);
        try {
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                //设置串口参数（波特率，数据位8，停止位1，校验位无）
                serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                System.out.println(String.format("开启端口: %s", commPort.getName()));
                return serialPort;
            }
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
        //是其他类型的端口
        throw new NoSuchPortException();
    }
}
