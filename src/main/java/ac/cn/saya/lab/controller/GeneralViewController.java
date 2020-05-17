package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.tools.Result;
import ac.cn.saya.lab.tools.ResultUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.List;
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
        Result<Object> result = RequestUrl.getDoshBoard();
        if (ResultUtil.checkSuccess(result)){
            // 请求成功
            JSONObject resultData = (JSONObject)result.getData();
            /**
             * 准备数据仪表盘的数据
             */
            int newsCount = (int)(resultData.getOrDefault("newsCount", 0));
            int pictureCount = (int)(resultData.getOrDefault("pictureCount", 0));
            int fileCount = (int)(resultData.getOrDefault("fileCount", 0));
            int bookCount = (int)(resultData.getOrDefault("bookCount", 0));
            int notesCount = (int)(resultData.getOrDefault("notesCount", 0));
            int planCount = (int)(resultData.getOrDefault("planCount", 0));
            List<Data> percentPieData = new ArrayList<>(6);
            percentPieData.add(new PieChart.Data("动态", newsCount));
            percentPieData.add(new PieChart.Data("图片", pictureCount));
            percentPieData.add(new PieChart.Data("文件", fileCount));
            percentPieData.add(new PieChart.Data("笔记", notesCount));
            percentPieData.add(new PieChart.Data("计划", planCount));
            percentPieData.add(new PieChart.Data("笔记簿", bookCount));
            percentPieData.add(new PieChart.Data("动态", newsCount));
            initPercentPie(percentPieData);
            /**
             * 构建财政数据
             */
            JSONArray financial = (JSONArray) resultData.getOrDefault("financial6",null);
            if (null == financial || financial.isEmpty()){

            }else {
                for (Object item:financial) {
                    JSONObject jsonObject = (JSONObject) item;
                    jsonObject.getString("describe");
                }
            }
            /**
             * 构造3个数据，存入，支出，总额
             */
            "deposited": 4137.56,
                    "expenditure": 630.49,
                    "tradeDate": "2018-09",
                    "currencyNumber": 5771.05
            System.out.println(resultData);
        }else {
            initPercentPie(null);
        }
        initFinancialBar();
        initLogLine();
        initNewsLine();
        initMemoBar();
        initUploadBar();
    }

    /**
     * 数据比重饼图初始化
     */
    private void initPercentPie(List<Data> percentPieData){
        ObservableList<Data> percentData = FXCollections.observableArrayList();
        if (null == percentPieData){
            percentData.addAll(new PieChart.Data("无", 0));
        }else {
            percentData.addAll(percentPieData);
        }
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
