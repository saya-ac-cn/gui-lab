package ac.cn.saya.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
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
    private PieChart percentPie;

    @FXML
    private LineChart<String, Number> logLine;

    @FXML
    private BarChart<String, Number> financialBar;

    @FXML
    private LineChart<String, Number> newsLine;

    @FXML
    private BarChart<String, Number> memoBar;

    @FXML
    private BarChart<String, Number> uploadBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPercentPie();
        initFinancialBar();
        initLogLine();
        initNewsLine();
        initMemoBar();
        initUploadBar();
    }

    /**
     * 数据比重饼图初始化
     */
    private void initPercentPie(){
        ObservableList<Data> percentData = FXCollections.observableArrayList();
        percentData.addAll(new PieChart.Data("java", 17.56),
                new PieChart.Data("JavaFx", 31.37));
        percentPie.setData(percentData);
    }

    /**
     * 财务金融柱状图初始化
     */
    private void initFinancialBar(){
        XYChart.Series<String, Number> saveData = new XYChart.Series<>();
        saveData.setName("存入");
        saveData.getData().add(new XYChart.Data<>("1月", 1.0));
        saveData.getData().add(new XYChart.Data<>("2月", 3.0));
        saveData.getData().add(new XYChart.Data<>("3月", 5.0));
        saveData.getData().add(new XYChart.Data<>("4月", 5.0));
        saveData.getData().add(new XYChart.Data<>("5月", 3.0));
        saveData.getData().add(new XYChart.Data<>("6月", 4.0));

        XYChart.Series<String, Number> takeData = new XYChart.Series<>();
        takeData.setName("支出");
        takeData.getData().add(new XYChart.Data<>("1月", 5.0));
        takeData.getData().add(new XYChart.Data<>("2月", 6.0));
        takeData.getData().add(new XYChart.Data<>("3月", 10.0));
        takeData.getData().add(new XYChart.Data<>("4月", 4.0));
        takeData.getData().add(new XYChart.Data<>("5月", 3.0));
        takeData.getData().add(new XYChart.Data<>("6月", 6.0));

        XYChart.Series<String, Number> totalData = new XYChart.Series<>();
        totalData.setName("总额");
        totalData.getData().add(new XYChart.Data<>("1月", 4.0));
        totalData.getData().add(new XYChart.Data<>("2月", 2.0));
        totalData.getData().add(new XYChart.Data<>("3月", 3.0));
        totalData.getData().add(new XYChart.Data<>("4月", 6.0));
        totalData.getData().add(new XYChart.Data<>("5月", 3.0));
        totalData.getData().add(new XYChart.Data<>("6月", 5.0));
        ObservableList<XYChart.Series<String, Number>> barData = FXCollections.observableArrayList();
        barData.add(saveData);
        barData.add(takeData);
        barData.add(totalData);
        financialBar.setData(barData);
    }

    /**
     * 活跃情况折线图初始化
     */
    private void initLogLine(){
        ObservableList<XYChart.Series<String, Number>> logLineData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> log = new LineChart.Series<String, Number>();
        log.setName("操作数");
        log.getData().add(new XYChart.Data<String, Number>("1", 40));
        log.getData().add(new XYChart.Data<String, Number>("2", 80));
        log.getData().add(new XYChart.Data<String, Number>("3", 100));
        log.getData().add(new XYChart.Data<String, Number>("4", 90));
        log.getData().add(new XYChart.Data<String, Number>("5", 110));
        log.getData().add(new XYChart.Data<String, Number>("6", 70));
        logLineData.add(log);
        logLine.setData(logLineData);
        logLine.createSymbolsProperty();
    }


    private void initNewsLine(){
        ObservableList<XYChart.Series<String, Number>> newsLineData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> news = new LineChart.Series<String, Number>();
        news.setName("发布数");
        news.getData().add(new XYChart.Data<String, Number>("1", 40));
        news.getData().add(new XYChart.Data<String, Number>("2", 80));
        news.getData().add(new XYChart.Data<String, Number>("3", 100));
        news.getData().add(new XYChart.Data<String, Number>("4", 90));
        news.getData().add(new XYChart.Data<String, Number>("5", 110));
        news.getData().add(new XYChart.Data<String, Number>("6", 70));
        newsLineData.add(news);
        newsLine.setData(newsLineData);
        newsLine.createSymbolsProperty();
    }

    /**
     * 便笺柱状图初始化
     */
    private void initMemoBar(){
        XYChart.Series<String, Number> memo = new XYChart.Series<>();
        memo.setName("发布数");
        memo.getData().add(new XYChart.Data<>("1月", 1.0));
        memo.getData().add(new XYChart.Data<>("2月", 3.0));
        memo.getData().add(new XYChart.Data<>("3月", 5.0));
        memo.getData().add(new XYChart.Data<>("4月", 5.0));
        memo.getData().add(new XYChart.Data<>("5月", 3.0));
        memo.getData().add(new XYChart.Data<>("6月", 4.0));
        ObservableList<XYChart.Series<String, Number>> memoData = FXCollections.observableArrayList();
        memoData.add(memo);
        memoBar.setData(memoData);
    }

    /**
     * 文件上传柱状图初始化
     */
    private void initUploadBar(){
        XYChart.Series<String, Number> upload = new XYChart.Series<>();
        upload.setName("上传数");
        upload.getData().add(new XYChart.Data<>("1月", 1.0));
        upload.getData().add(new XYChart.Data<>("2月", 3.0));
        upload.getData().add(new XYChart.Data<>("3月", 5.0));
        upload.getData().add(new XYChart.Data<>("4月", 5.0));
        upload.getData().add(new XYChart.Data<>("5月", 3.0));
        upload.getData().add(new XYChart.Data<>("6月", 4.0));
        ObservableList<XYChart.Series<String, Number>> uploadData = FXCollections.observableArrayList();
        uploadData.add(upload);
        uploadBar.setData(uploadData);
    }

}
