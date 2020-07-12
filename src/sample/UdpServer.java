package sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer implements Runnable {

    private int port;

    private MainApp mainApp;

    public UdpServer(MainApp mainApp,int port) {
        this.port = port;
        this.mainApp = mainApp;
    }

    @Override
    public void run() {
        try {

            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];
            System.out.println("Добро пожаловать на Серверную чаcть");
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());

                System.out.println("RECEIVED: " + sentence);
               //mainApp.getvideo().add(sentence);
                mainApp.initchar(sentence);
            }
        } catch (Exception e) {
            System.out.println("Оххх");
            e.printStackTrace();
        }
    }
}

