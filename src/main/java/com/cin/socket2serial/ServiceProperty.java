package com.cin.socket2serial;

import com.cin.socket2serial.util.PropertiesUtil;

public class ServiceProperty {

    private static final String PROPERTY_PATH = "service.Properties";
    private String serialPort;
    private int baudRate;
    private int bufferSize;
    private boolean debug;
    private int servicePort;


    public ServiceProperty() {
        PropertiesUtil.mapperObject(PROPERTY_PATH, this);
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void save() {
        PropertiesUtil.saveToFile(PROPERTY_PATH, this);
    }

    @Override
    public String toString() {
        return "{" +
                "serialPort='" + serialPort + '\'' +
                ", baudRate=" + baudRate +
                ", bufferSize=" + bufferSize +
                ", debug=" + debug +
                ", servicePort=" + servicePort +
                '}';
    }
}
