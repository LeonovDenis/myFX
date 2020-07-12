package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.Arrays;

public class videoController {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> chennels = FXCollections.observableArrayList();

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл был загружен.
     */
    @FXML
    private void initialize() {
        // Получаем массив с английскими именами месяцев.
        for (int i = 0; i <288 ; i++) {


        // Преобразуем его в список и добавляем в наш ObservableList месяцев.
        chennels.add(String.valueOf(i+1));
    }
        // Назначаем имена месяцев категориями для горизонтальной оси.
        xAxis.setCategories(chennels);
    }


    public void setPersonData(String data) {
        // Считаем адресатов, имеющих дни рождения в указанном месяце.

        byte[] b=data.getBytes();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Создаём объект XYChart.Data для каждого месяца.
        // Добавляем его в серии.
        for (int i = 4; i < chennels.size(); i++) {
            series.getData().add(new XYChart.Data<>(chennels.get(i-4), (int)b[i]));
        }

        barChart.getData().add(series);
    }
}
