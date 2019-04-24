package com.cin.socket2serial;

import com.cin.socket2serial.service.PrinterService;
import com.cin.socket2serial.service.SocketService;
import com.cin.socket2serial.util.LogUtil;

public class Application {

    private ServiceProperty property;
    private static Application application;
    private SocketService socketService;
    private PrinterService printerService;

    public static void main(String[] args) {
        application = new Application();
        application.initialize();
        application.run();
    }

    public static Application get() {
        return application;
    }

    private Application() {
        property = new ServiceProperty();
        LogUtil.debug("加载配置: " + property.toString());
    }

    private void initialize() {
        socketService = new SocketService();
        printerService = new PrinterService();
    }

    public void reloadConfig(ServiceProperty property) {
        this.property = property;
        printerService.reload();
        property.save();
    }

    public PrinterService getPrinterService() {
        return printerService;
    }

    private void run() {
        socketService.start();
        printerService.start();
    }

    public static ServiceProperty getProperty() {
        Application application = get();
        if (application == null) {
            throw new NullPointerException("Please initialize first application.initialize()");
        }
        return get().property;
    }

}
