package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.tools.DateUtils;
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

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
        if (ResultUtil.checkSuccess(result)) {
            // 请求成功
            JSONObject resultData = (JSONObject) result.getData();
            /**
             * 准备数据仪表盘的数据
             */
            int newsCount = (int) (resultData.getOrDefault("newsCount", 0));
            int pictureCount = (int) (resultData.getOrDefault("pictureCount", 0));
            int fileCount = (int) (resultData.getOrDefault("fileCount", 0));
            int bookCount = (int) (resultData.getOrDefault("bookCount", 0));
            int notesCount = (int) (resultData.getOrDefault("notesCount", 0));
            int planCount = (int) (resultData.getOrDefault("planCount", 0));
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
            JSONArray financial = (JSONArray) resultData.getOrDefault("financial6", null);
            XYChart.Series<String, Number> saveData = new XYChart.Series<>();
            XYChart.Series<String, Number> takeData = new XYChart.Series<>();
            XYChart.Series<String, Number> totalData = new XYChart.Series<>();
            if (null == financial || financial.isEmpty()) {
                initFinancialBar(null,null,null);
            } else {
                String tradeDate = null;
                BigDecimal deposited,expenditure,currencyNumber;
                for (Object item : financial) {
                    JSONObject jsonObject = (JSONObject) item;
                    // 统计时间
                    tradeDate = (String) jsonObject.getOrDefault("tradeDate", "");
                    // 构造流入
                    deposited = jsonObject.getBigDecimal("deposited");
                    saveData.getData().add(new XYChart.Data<>(tradeDate, deposited.doubleValue()));
                    // 构造流出
                    expenditure = jsonObject.getBigDecimal("expenditure");
                    takeData.getData().add(new XYChart.Data<>(tradeDate, expenditure.doubleValue()));
                    // 构造总额
                    currencyNumber = jsonObject.getBigDecimal("currencyNumber");
                    totalData.getData().add(new XYChart.Data<>(tradeDate, currencyNumber.doubleValue()));
                }
                initFinancialBar(saveData,takeData,totalData);
            }
            /**
             * 活跃情况折线图初始化
             */
            LineChart.Series<String, Number> log = new LineChart.Series<String, Number>();
            JSONObject log6 = (JSONObject) resultData.getOrDefault("log6", null);
            if (null == log6 || log6.isEmpty()){
                initLogLine(null);
            }else {
                Set<String> keySet = log6.keySet();
                for (String date:keySet) {
                    log.getData().add(new XYChart.Data<String, Number>(date, log6.getIntValue(date)));
                }
                initLogLine(log);
            }
            /**
             * 动态发布数初始化
             */
            LineChart.Series<String, Number> news = new LineChart.Series<String, Number>();
            JSONObject news6 = (JSONObject) resultData.getOrDefault("news6", null);
            if (null == news6 || news6.isEmpty()){
                initNewsLine(null);
            }else {
                Set<String> keySet = news6.keySet();
                for (String date:keySet) {
                    news.getData().add(new XYChart.Data<String, Number>(date, news6.getIntValue(date)));
                }
                initNewsLine(news);
            }
            System.out.println(resultData);
        } else {
            initPercentPie(null);
            initFinancialBar(null,null,null);
            initLogLine(null);
            initNewsLine(null);
        }
        initMemoBar();
        initUploadBar();
    }

    /**
     * 数据比重饼图初始化
     */
    private void initPercentPie(List<Data> percentPieData) {
        ObservableList<Data> percentData = FXCollections.observableArrayList();
        if (null == percentPieData) {
            percentData.addAll(new PieChart.Data("无", 0));
        } else {
            percentData.addAll(percentPieData);
        }
        percentPie.setData(percentData);
    }

    /**
     * 财务金融柱状图初始化
     */
    private void initFinancialBar(XYChart.Series<String, Number> saveData,XYChart.Series<String, Number> takeData,XYChart.Series<String, Number> totalData) {
        if (null == saveData || null == takeData || null == totalData){
            saveData = new XYChart.Series<>();
            takeData = new XYChart.Series<>();
            totalData = new XYChart.Series<>();
            // 重新构造数据
            String[] halfYearData = DateUtils.getHalfYearData();
            for (String item:halfYearData) {
                saveData.getData().add(new XYChart.Data<>(item, 0.0));
                takeData.getData().add(new XYChart.Data<>(item, 0.0));
                totalData.getData().add(new XYChart.Data<>(item, 0.0));
            }
        }
        saveData.setName("存入");
        takeData.setName("支出");
        totalData.setName("总额");
        ObservableList<XYChart.Series<String, Number>> barData = FXCollections.observableArrayList();
        barData.add(saveData);
        barData.add(takeData);
        barData.add(totalData);
        financialBar.setData(barData);
    }

    /**
     * 活跃情况折线图初始化
     */
    private void initLogLine(LineChart.Series<String, Number>  log) {
        ObservableList<XYChart.Series<String, Number>> logLineData = FXCollections.observableArrayList();
        if (null == log){
            log = new LineChart.Series<String, Number>();
            // 重新构造数据
            String[] halfYearData = DateUtils.getHalfYearData();
            for (String item:halfYearData) {
                log.getData().add(new XYChart.Data<String, Number>(item, 0));
            }
        }
        log.setName("操作数");
        logLineData.add(log);
        logLine.setData(logLineData);
        logLine.createSymbolsProperty();
    }


    private void initNewsLine(LineChart.Series<String, Number> news) {
        ObservableList<XYChart.Series<String, Number>> newsLineData = FXCollections.observableArrayList();
        if (null == news){
            news = new LineChart.Series<String, Number>();
            // 重新构造数据
            String[] halfYearData = DateUtils.getHalfYearData();
            for (String item:halfYearData) {
                news.getData().add(new XYChart.Data<String, Number>(item, 0));
            }
        }
        news.setName("发布数");
        newsLineData.add(news);
        newsLine.setData(newsLineData);
        newsLine.createSymbolsProperty();
    }

    /**
     * 便笺柱状图初始化
     */
    private void initMemoBar() {
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
    private void initUploadBar() {
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
