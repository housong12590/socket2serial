package com.cin.socket2serial.service;

import com.cin.socket2serial.Application;
import com.cin.socket2serial.service.protocol.Protocol;
import com.cin.socket2serial.service.protocol.ProtocolImpl;
import com.cin.socket2serial.util.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class SocketService implements Runnable {

    private Selector selector;
    private static final int timeOut = 3000;
    private Protocol protocol;

    public void start() {
        new Thread(this).start();
    }

    public SocketService() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            int servicePort = Application.getProperty().getServicePort();
            serverChannel.socket().bind(new InetSocketAddress(servicePort));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            int bufferSize = Application.getProperty().getBufferSize();
            protocol = new ProtocolImpl(bufferSize);
            LogUtil.info(String.format("服务器开启,  监听%s端口...", servicePort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select(timeOut) == 0) {
                    continue;
                }
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {
                                protocol.handleAccept(key);
                            } else if (key.isReadable()) {
                                protocol.handleRead(key);
                            } else if (key.isWritable()) {
                                protocol.handleWrite(key);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            key.channel().close();
                            key.cancel();
                        } finally {
                            keys.remove();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
