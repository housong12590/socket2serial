package com.cin.socket2serial;


import com.cin.socket2serial.util.CmdUtils;

import java.io.IOException;

public class Index {

    public static void main(String[] args) throws IOException {
        boolean printer_repeater = CmdUtils.checkExistService("printer_repeater");
        System.out.println(printer_repeater);
    }


}
