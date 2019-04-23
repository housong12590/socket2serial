package com.cin.socket2serial.service.protocol;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Protocol {
    /**
     * 接收socketChannel
     *
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;

    /**
     * 从一个socketChannel读信息
     *
     * @param key
     * @throws IOException
     */
    void handleRead(SelectionKey key) throws IOException;

    /**
     * 向socketChannel写入信息
     *
     * @param key
     * @throws IOException
     */
    void handleWrite(SelectionKey key) throws IOException;

}
