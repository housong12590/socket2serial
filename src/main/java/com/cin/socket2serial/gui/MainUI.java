package com.cin.socket2serial.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JPanel panel;
    private JComboBox comboBox1;
    private JButton restart;


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainUI");
        frame.setSize(600,400);
        frame.setLocation(600,400);
        frame.setContentPane(new MainUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("文件"));
        menuBar.add(new JMenu("文件"));
        menuBar.add(new JMenu("文件"));
        menuBar.add(new JMenu("文件"));
        menuBar.add(new JMenu("文件"));
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public MainUI() {
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.paramString());
            }
        });
    }
}
