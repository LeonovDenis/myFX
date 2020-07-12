package sample;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
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
    private AreaChart<Integer, Integer> AreaChart;

    @FXML
    private CategoryAxis xAxis;

    private  ObservableList<Integer> chennels = FXCollections.observableArrayList();




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

       for (int i = 0; i <288 ; i++) {


           // Преобразуем его в список и добавляем в наш ObservableList месяцев.
           chennels.add(i);
       }
       // Назначаем имена месяцев категориями для горизонтальной оси.
       System.out.println(chennels.size());
         //xAxis.setCategories(chennels);


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



        Thread thread = null;
        try {
            thread = new Thread(new UdpSender(mainApp,InetAddress.getByName(ipARM.getText()),Integer.parseInt(portARM.getText()),temple), "Дополнительный поток");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Создан дополнительный поток отправки " +
                thread);
        thread.start();

    }

    @FXML
    private void handleVideoButton() {

        Thread thread = new Thread(new UdpServer(mainApp,Integer.parseInt(portVideo.getText())), "Дополнительный поток");
        System.out.println("Создан дополнительный поток " +
                thread);
        thread.start();


    }

    public  void setPersonData(String data) {
        // Считаем адресатов, имеющих дни рождения в указанном месяце.
        byte[] b=new byte[chennels.size()];
        b=data.getBytes();
        System.out.println(b.length);
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();

        // Создаём объект XYChart.Data для каждого месяца.
        // Добавляем его в серии.
        for (int i = 0; i < 288; i++) {
            series.getData().add(new XYChart.Data<>(i, i<b.length?(int)b[i]:0));
        }
        System.out.println("sdfsdsdsd");
        AreaChart.getData().add(series);
    }

}
