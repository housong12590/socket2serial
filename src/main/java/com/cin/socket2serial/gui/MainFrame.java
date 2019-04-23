package com.cin.socket2serial.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        new MainFrame().init();
    }

    public void init() {
        setTitle("打印服务设置");
        setVisible(true);
        setSize(600,400);
        setLocation(400,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = getContentPane();
        JLabel label = new JLabel("波特率");
        container.add(label);
    }
}
