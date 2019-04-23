package com.cin.socket2serial;

import com.cin.socket2serial.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 串口服务类，提供打开、关闭串口，读取、发送串口数据等服务（采用单例设计模式）
 *
 * @author zhong
 */
public class SerialTool {

    private static SerialTool serialTool = new SerialTool();


    //私有化SerialTool类的构造方法，不允许其他类生成SerialTool对象
    private SerialTool() {
    }

    /**
     * 获取提供服务的SerialTool对象
     *
     * @return serialTool
     */
    public static SerialTool getSerialTool() {
        if (serialTool == null) {
            serialTool = new SerialTool();
        }
        return serialTool;
    }


    /**
     * 查找所有可用端口
     *
     * @return 可用端口名称列表
     */
    public List<String> findSerialPort() {
        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        List<String> portNameList = new ArrayList<>();
        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }


    /**
     * 开启串口
     *
     * @param serialPortName 串口名称
     * @param baudRate       波特率
     * @return 串口对象
     */
    public SerialPort openSerialPort(String serialPortName, int baudRate) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        //通过端口名称得到端口
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
        //打开端口，（自定义名字，打开超时时间）
        CommPort commPort = portIdentifier.open(serialPortName, 2222);
        //判断是不是串口
        if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) commPort;
            //设置串口参数（波特率，数据位8，停止位1，校验位无）
            serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            System.out.println("开启串口成功，串口名称：" + serialPortName);
            return serialPort;
        }
        //是其他类型的端口
        throw new NoSuchPortException();
    }

    /**
     * 关闭串口
     *
     * @param serialPort 要关闭的串口对象
     */
    public void closeSerialPort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            System.out.println("关闭了串口：" + serialPort.getName());
        }
    }


    /**
     * 向串口发送数据
     *
     * @param serialPort 串口对象
     * @param data       发送的数据
     */
    public void sendData(SerialPort serialPort, byte[] data) {
        OutputStream os = null;
        try {
            os = serialPort.getOutputStream();//获得串口的输出流
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(os);
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 要读取的串口
     * @return 读取的数据
     */
    public byte[] readData(SerialPort serialPort) {
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = serialPort.getInputStream();//获得串口的输入流
            int buffLen = is.available();//获得数据长度
            while (buffLen != 0) {
                bytes = new byte[buffLen];//初始化byte数组
                is.read(bytes);
                buffLen = is.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(is);
        }
        return bytes;
    }

    /**
     * 给串口设置监听
     *
     * @param serialPort
     * @param listener
     */
    public void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
        try {
            //给串口添加事件监听
            serialPort.addEventListener(listener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        serialPort.notifyOnDataAvailable(true);//串口有数据监听
        serialPort.notifyOnBreakInterrupt(true);//中断事件监听
    }

}
