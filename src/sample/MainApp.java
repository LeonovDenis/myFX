package sample;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    overviewContr controller;
    private ObservableList<message> send = FXCollections.observableArrayList();


    private ObservableList<String> video = FXCollections.observableArrayList();

  @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Test App");

        initRootLayout();

       // showPersonOverview();

      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent t) {
              System.out.println(send.toString());
              System.out.println(video.toString());
              Platform.exit();
              System.exit(0);
          }
      });

    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {

            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();

           // loader.setLocation(MainApp.class.getResource("RootLayer.fxml"));

            loader.setLocation(MainApp.class.getResource("sample.fxml"));

           // rootLayout = (BorderPane) loader.load();
            AnchorPane rootLayout = (AnchorPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            controller = loader.getController();
            controller.setMainApp(this);
            controller.setPersonData("aasassas");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
   /* public void showPersonOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("sample.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            overviewContr controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<message> getsend() {
        return send;
    }
    public ObservableList<String> getvideo() {

        return video;
    }

   public MainApp() {


   }
   void initchar(String sentence){
       this.getvideo().add(sentence);
      controller.setPersonData(sentence);
       System.out.println("cvcvcvcv");
   }

}
