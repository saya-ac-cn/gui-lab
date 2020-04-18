package ac.cn.saya.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: GeneralViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 13:42
 * @Description: 平台总览
 */

public class GeneralViewController implements Initializable {

    @FXML
    private LineChart<String, Number> financialGraph;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> series1 = new LineChart.Series<String, Number>();
        series1.setName(null);
        series1.getData().add(new XYChart.Data<String, Number>("1", 40));
        series1.getData().add(new XYChart.Data<String, Number>("2", 80));
        series1.getData().add(new XYChart.Data<String, Number>("3", 100));
        series1.getData().add(new XYChart.Data<String, Number>("4", 90));
        series1.getData().add(new XYChart.Data<String, Number>("5", 110));
        series1.getData().add(new XYChart.Data<String, Number>("6", 70));
        lineChartData.add(series1);
        financialGraph.setData(lineChartData);
        financialGraph.createSymbolsProperty();
        // https://docs.oracle.com/javafx/2/charts/css-styles.htm
    }
}