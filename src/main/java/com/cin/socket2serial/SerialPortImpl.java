package com.cin.socket2serial;

import com.cin.socket2serial.util.IOUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortImpl implements Port {
    @Override
    public CommPort open(String portName,  Map<String, Object> options) throws PortInUseException, NoSuchPortException {
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
                System.out.println("开启串口成功，串口名称：" + portName);
                return serialPort;
            }
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
        //是其他类型的端口
        throw new NoSuchPortException();
    }

    @Override
    public void close(CommPort commPort) {
        if (commPort != null) {
            commPort.close();
            System.out.println("关闭了串口：" + commPort.getName());
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
