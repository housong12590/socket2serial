package com.cin.socket2serial;

import com.cin.socket2serial.util.PropertiesUtil;

public class ServiceProperty {

    private static final String PROPERTY_PATH = "service.Properties";
    private String portName;
    private int baudRate;
    private int bufferSize;
    private boolean debug;
    private int servicePort;
    private String mode = "serial";// serial 串口 parallel 并口


    public ServiceProperty() {
        PropertiesUtil.mapperObject(PROPERTY_PATH, this);
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void save() {
        PropertiesUtil.saveToFile(PROPERTY_PATH, this);
    }

    @Override
    public String toString() {
        return "{" +
                "portName='" + portName + '\'' +
                ", baudRate=" + baudRate +
                ", bufferSize=" + bufferSize +
                ", debug=" + debug +
                ", servicePort=" + servicePort +
                ", mode='" + mode + '\'' +
                '}';
    }
}
