package com.cin.socket2serial;

public class Application {

    public static void main(String[] args) {

        while (true) {
            System.out.println("hello java");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
