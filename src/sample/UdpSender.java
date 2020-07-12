package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

public class UdpSender implements Runnable{

    private InetAddress addr;
    private int port;
    private message mes;
    private MainApp mainApp;

    public UdpSender(MainApp mainApp,InetAddress addr, int port, message mes) throws IOException {
        this.addr = addr;
        this.port = port;
        this.mes = mes;
        this.mainApp = mainApp;
    }



    @Override
    public void run() {
        try {
            System.out.println("Отправка");

            DatagramSocket clientSocket = new DatagramSocket();
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = mes.getFull();

/*
            String input = sentence; // my UTF-16 string
            StringBuilder sb = new StringBuilder(input.length());
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch <= 0xFF) {
                    sb.append(ch);
                }
            }

            byte[] ascii = sb.toString().getBytes("ISO-8859-1"); // aka LATIN-1

            sendData =ascii;
*/
              sendData = sentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                clientSocket.setSoTimeout(300);

                clientSocket.receive(receivePacket);


                String modifiedSentence = new String(receivePacket.getData());
                mes.setOtvet(modifiedSentence);


                mainApp.getsend().add(mes);

            } catch (SocketTimeoutException e) {

                // если время ожидания вышло
                System.out.println("Истекло время ожидания, прием данных закончен");
                mes.setOtvet("Нет ответа");
                mainApp.getsend().add(mes);
            }

        } catch (Exception e) {
            System.out.println("Оххх1");
            e.printStackTrace();
        }
    }


}

