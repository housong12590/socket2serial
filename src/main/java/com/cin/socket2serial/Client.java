package com.cin.socket2serial;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1; i++) {
            Client client = new Client();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public Client() throws IOException {
        byte[] bytes1 = new byte[]{27, 69, 1, 28, 45, 0, 29, 33, 17, 27, 97, 1, -74, -32, -73, -84, -63, -49, -64, -19, -78, -51, -52, -4, 10, 27, 69, 0, 28, 45, 0, 29, 33, 0, 27, 97, 1, -50, -94, -48, -59, -55, -88, -62, -21, -55, -49, -73, -67, -74, -2, -50, -84, -62, -21, -93, -84, -74, -44, -79, -66, -76, -50, -49, -5, -73, -47, -58, -64, -68, -37, 10, 27, 100, 6, 27, 109};
        Socket socket = new Socket("192.168.10.201", 9100);
        OutputStream out = socket.getOutputStream();
        out.write(bytes1);
        out.flush();
        out.close();
        socket.close();
    }
}
