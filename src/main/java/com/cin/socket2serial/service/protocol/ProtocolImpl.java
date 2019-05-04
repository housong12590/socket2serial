package com.cin.socket2serial.service.protocol;

import com.cin.socket2serial.Application;
import com.cin.socket2serial.service.PrinterService;
import com.cin.socket2serial.util.LogUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ProtocolImpl implements Protocol {
    private ByteBuffer buffer;

    public ProtocolImpl(int bufferSize) {
        buffer = ByteBuffer.allocate(bufferSize);
    }

    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        channel.configureBlocking(false);
        LogUtil.info(String.format("客户端%s已连接", channel.socket().getRemoteSocketAddress()));
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.clear();
        while ((channel.read(buffer)) != -1) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            buffer.clear();
            LogUtil.debug(String.format("client : %s 发送了长度为%s的数据", channel.socket().getRemoteSocketAddress(), data.length));
            sendToSerial(data);
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    public void handleWrite(SelectionKey key) throws IOException {
//        SocketChannel channel = (SocketChannel) key.channel();
//        ByteBuffer writeBuffer = ByteBuffer.wrap(data);
//        channel.write(writeBuffer);
//        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private void sendToSerial(byte[] data) {
        PrinterService printerService = Application.get().getPrinterService();
        printerService.add(data);
    }
}
