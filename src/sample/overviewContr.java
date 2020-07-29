package sample;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;

public class overviewContr {

    @FXML
    private TableView<message> messageTable;
    @FXML
    private TableColumn<message, String> sendColumn;
    @FXML
    private TableColumn<message, String> resiveColumn;

    @FXML
    private TextField header;
    @FXML
    private TextField func;
    @FXML
    private TextField reserv;
    @FXML
    private TextField error;
    @FXML
    private TextField data;
    @FXML
    private TextField ipARM;
    @FXML
    private TextField portARM;
    @FXML
    private TextField portVideo;



    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    static XYChart.Series<Number, Number> series;

    @FXML
    private AreaChart<Number, Number> AreaChart;



    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public overviewContr() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
   @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
        sendColumn.setCellValueFactory(cellData -> cellData.getValue().fullProperty());

        resiveColumn.setCellValueFactory(cellData -> cellData.getValue().otvetProperty());


       series = new XYChart.Series<>();

       AreaChart.setAnimated(false);
       AreaChart.getData().add(series);


    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        messageTable.setItems(mainApp.getsend());
    }
    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleSendButton() {

        message temple =new message(header.getText(), func.getText(),reserv.getText(),error.getText(),data.getText(),null);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable sender= null;
                try {
                    sender = new UdpSender(mainApp, InetAddress.getByName(ipARM.getText()),Integer.parseInt(portARM.getText()),temple);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.runLater(sender);
            }
        });

        thread.setDaemon(true);
        thread.start();
        System.out.println("Создан дополнительный поток отправки " +
                thread);

    }

    @FXML
    private void handleVideoButton() {

        mainApp.setStop(false);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable resiever = new UdpServer(mainApp,Integer.parseInt(portVideo.getText()));
                Thread thread2=new Thread(resiever,"поток");
                thread2.setDaemon(true);
                thread2.start();
            }
        });

        thread.setDaemon(true);
        thread.start();
        System.out.println("Создан дополнительный поток приема " +
                thread);




    }
    @FXML
    private void setStop(){
        mainApp.setStop(true);
    }


}
