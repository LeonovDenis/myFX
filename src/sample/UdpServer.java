package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UdpServer implements Runnable {

    private int port;

    private MainApp mainApp;
    private Short[] videodata = new Short[576];

    public UdpServer(MainApp mainApp, int port) {
        this.port = port;
        this.mainApp = mainApp;
    }

    @Override
    public void run() {
        DatagramSocket serverSocket=null;
        DatagramPacket receivePacket=null;

        try {

            serverSocket = new DatagramSocket(port);


            byte[] receiveData = new byte[1156];
            System.out.println("Добро пожаловать на Серверную чаcть");
            while (!mainApp.isStop()) {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String sentence = new String(Hex.encodeHex(receivePacket.getData()));
                System.out.println("RECEIVED: " + sentence);

                if (sentence.toUpperCase().startsWith("3FFF")) {
                    videodata = byteToShort(receivePacket.getData());

                    //mainApp.getvideo().clear();
                    mainApp.getvideo().setAll(videodata);
                } else {
                    videodata = byteToShort(receivePacket.getData());

                    //mainApp.getvideo().clear();
                    mainApp.getvideo().setAll(videodata);
                }
                Thread r =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("adding");

                        overviewContr.series.getData().clear();
                        for (int i = 0; i <288 ; i++) {


                     overviewContr.series.getData().add(new XYChart.Data<>(i,videodata[i]!=null?videodata[i]:0))   ;
                    }


                    }
                });
                Platform.runLater(r);
                System.out.println("runs");

            }

        } catch (Exception e) {
            System.out.println("Оххх");
            //e.printStackTrace();
            if(serverSocket!=null){
                serverSocket.close();
                System.out.println("erttttt");
            }
        }
        finally{if(serverSocket!=null){
            serverSocket.close();
            System.out.println("tratata");
        };


        }

    }

    public Short[] byteToShort(byte[] b) {
        Short[] x = new Short[(b.length) / 2];
        int k=0;
        for (int i = 4; i < b.length; i = i + 2) {
            x[k++] = (short) (128 * ((byte) (b[i] & (byte) 0x7f)) + b[i + 1]);
            System.out.println("--"+k+"//"+x[k-1]);
        }
        return x;
    }
}

