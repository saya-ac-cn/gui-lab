package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.assemb.AdvisorPagingAndDate;
import ac.cn.saya.lab.entity.TransactionListEntity;
import ac.cn.saya.lab.entity.UserEntity;
import ac.cn.saya.lab.tools.DateUtils;
import ac.cn.saya.lab.tools.PagingTools;
import ac.cn.saya.lab.tools.Result;
import ac.cn.saya.lab.tools.ResultUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Title: TransactionForDayViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 12:39
 * @Description: 日度报表
 */

public class TransactionForDayViewController extends AdvisorPagingAndDate implements Initializable {

    /**
     * 表格
     */
    @FXML
    private TableView<TransactionListEntity> dataTableView;

    /**
     * 数据
     */
    private ObservableList<TransactionListEntity> data = FXCollections.observableArrayList();

    /**
     * 存入
     */
    @FXML
    private TableColumn<TransactionListEntity, Double> deposited;

    /**
     * 支出
     */
    @FXML
    private TableColumn<TransactionListEntity, Double> expenditure;

    /**
     * 产生总额
     */
    @FXML
    private TableColumn<TransactionListEntity, Double> currencyNumber;

    /**
     * 交易日期
     */
    @FXML
    private TableColumn<TransactionListEntity, String> tradeDate;

    /**
     * 查询条件
     */
    private JSONObject queryCondition = new JSONObject();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deposited.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("deposited"));
        expenditure.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("expenditure"));
        currencyNumber.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("currencyNumber"));
        tradeDate.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, String>("tradeDate"));
        dataTableView.setItems(data);
        Platform.runLater(() -> handleSearchAction());
        initDatePicker();
    }

    /**
     * 渲染表格
     *
     * @param pageNum 用户想要得到的页
     */
    private void getData(int pageNum) {
        queryCondition.put("nowPage",pageNum);
        queryCondition.put("pageSize",10);
        // 请求数据查询
        Result<Object> requestResult = RequestUrl.totalTransactionForDay(queryCondition);
        if (ResultUtil.checkSuccess(requestResult)){
            // 请求成功
            JSONObject requestData = (JSONObject)requestResult.getData();
            JSONArray grid = (JSONArray) requestData.getOrDefault("grid",null);
            List<TransactionListEntity> gridList = JSONObject.parseArray(grid.toJSONString(), TransactionListEntity.class);
            data.clear();
            data.addAll(gridList);
            dataTableView.setItems(data);
            // 请求成功后更新当前页码 和 总页数
            pageIndex = requestData.getInteger("pageNow");
            pageCount = requestData.getInteger("totalPage");
            displayTable(true);
        }else {
            data.clear();
            dataTableView.setItems(data);
            displayTable(false);
        }
    }

    /**
     * 分页事件
     * @param event
     */
    public void handlePageJump(ActionEvent event) {
        //event.getSource()获取一个Object对象 实际就是这个button 这里我们需要强制转行
        Button bu = (Button) event.getSource();
        // 即将要跳转的位置
        int newIndex = computeJumpNum(bu.getId());
        getData(newIndex);
    }

    /**
     * 搜索事件，触发搜索时，表示页面的筛选条件已经改变，需从第一页开始
     */
    public void handleSearchAction(){
        queryCondition.clear();
        String _beginTime = beginTime.getValue().format(DateUtils.dateFormat);
        String _endTime = endTime.getValue().format(DateUtils.dateFormat);
        queryCondition.put("beginTime",_beginTime);
        queryCondition.put("endTime",_endTime);
        getData(1);
    }

    /**
     * 重置搜索条件
     */
    public void handleRestSearchAction(){
        // 清空后台的筛选条件
        queryCondition.clear();
        beginTime.setValue(LocalDate.now().minusMonths(1));
        endTime.setValue(LocalDate.now());
        handleSearchAction();
    }

    /**
     * 导出
     */
    public void handleOutAction(){
        JSONObject outCondition = new JSONObject();
        String _beginTime = beginTime.getValue().format(DateUtils.dateFormat);
        String _endTime = endTime.getValue().format(DateUtils.dateFormat);
        outCondition.put("beginTime",_beginTime);
        outCondition.put("endTime",_endTime);
        //得到用户导出的文件路径
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("报表导出");
        fileChooser.setInitialFileName("财务流水日度报表.xlsx");
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if(file==null) {
            return;
        }
        // 得到输出文件路径
        String exportFilePath = file.getAbsolutePath().replaceAll(".xlsx", "")+".xlsx";
        RequestUrl.downTransactionForDayExcel(outCondition,exportFilePath);
    }

}
