package sample;

import java.io.IOException;
import java.net.*;
import org.apache.commons.codec.binary.Hex;

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
            byte[] sendData = new byte[248];
            byte[] receiveData = new byte[248];
            String sentence = mes.getFull();

            sendData=Hex.decodeHex(sentence);

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                clientSocket.setSoTimeout(300);

                clientSocket.receive(receivePacket);

                String modifiedSentence = new String(Hex.encodeHex(receivePacket.getData()));
                mes.setOtvet(modifiedSentence);


            } catch (SocketTimeoutException e) {

                // если время ожидания вышло
                System.out.println("Истекло время ожидания, прием данных закончен");
                mes.setOtvet("Нет ответа");

            }

        } catch (Exception e) {
            System.out.println("Оххх1");
            e.printStackTrace();
        }
        finally{
            mainApp.getsend().add(mes);
        }
    }


}

