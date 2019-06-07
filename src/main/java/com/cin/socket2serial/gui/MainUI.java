package com.cin.socket2serial.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainUI {
    private JPanel panel;
    private JTextField hostname;
    private JComboBox baudRate;
    private JButton restart;
    private JComboBox portType;
    private JLabel baudRateLable;


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainUI");
        frame.setSize(600, 400);
        frame.setLocation(600, 400);
        MainUI mainUI = new MainUI();
//        mainUI.panel.setSize(600, 400);
        frame.add(mainUI.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    private MainUI() {

        devicePortChange((String) portType.getSelectedItem());
        portType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) portType.getSelectedItem();
                devicePortChange(type);
            }
        });


        baudRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = baudRate.getSelectedItem();
                System.out.println(selectedItem);
            }
        });
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void devicePortChange(String type) {
        if ("网口".equals(type)) {
            hostname.setVisible(true);
            hostname.setOpaque(false);
        } else if ("串口".equals(type)) {
            hostname.setVisible(false);
            hostname.hide();
        } else if ("并口".equals(type)) {
            hostname.setVisible(false);
            hostname.hide();
        }
        System.out.println(type);
    }

}
