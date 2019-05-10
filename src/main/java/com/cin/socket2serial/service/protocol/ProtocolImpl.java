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
        int readLen;
        do {
            readLen = channel.read(buffer);
            if (readLen == -1) {
                channel.close();
            } else if (readLen > 0) {
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                buffer.clear();
                LogUtil.debug(String.format("client : %s 发送了长度为%s的数据", channel.socket().getRemoteSocketAddress(), data.length));
                checkDataLengthSplit(data);
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            }
        } while (readLen > 0);
    }

    private void checkDataLengthSplit(byte[] bytes) {
        int childLen = 512;
        int position = 0;
        if (bytes.length > childLen) {
            int count = bytes.length / childLen + (bytes.length % childLen == 0 ? 0 : 1);
            for (int i = 0; i < count; i++) {
                if (bytes.length - position < childLen) {
                    childLen = bytes.length - position;
                }
                byte[] new_arr = new byte[childLen];
                System.arraycopy(bytes, position, new_arr, 0, childLen);
                sendToSerial(new_arr);
                position += childLen;
            }
        } else {
            sendToSerial(bytes);
        }
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
