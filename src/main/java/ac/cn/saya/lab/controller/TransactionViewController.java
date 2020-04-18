package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.assemb.AdvisorPagingAndDate;
import ac.cn.saya.lab.control.EditTransactionControl;
import ac.cn.saya.lab.control.EditTransactionInfoControl;
import ac.cn.saya.lab.entity.TransactionListEntity;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import ac.cn.saya.lab.entity.UserEntity;
import ac.cn.saya.lab.tools.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Title: TransactionViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 12:39
 * @Description: 财务流水报表
 */

public class TransactionViewController extends AdvisorPagingAndDate implements Initializable {

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
     * 流水编号
     */
    @FXML
    private TableColumn<TransactionListEntity, Integer> tradeId;

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
     * 交易方式
     */
    @FXML
    private TableColumn<TransactionListEntity, String> transactionType;

    /**
     * 产生总额
     */
    @FXML
    private TableColumn<TransactionListEntity, Double> currencyNumber;

    /**
     * 摘要
     */
    @FXML
    private TableColumn<TransactionListEntity, String> transactionAmount;

    /**
     * 交易日期
     */
    @FXML
    private TableColumn<TransactionListEntity, String> tradeDate;

    @FXML
    private TableColumn<TransactionListEntity, String> operateCol;

    /**
     * 交易类别
     */
    @FXML
    private ChoiceBox<TransactionTypeEntity> dealType;

    /**
     * 查询条件
     */
    private JSONObject queryCondition = new JSONObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 页面选择器初始化
        dealType.getItems().addAll(SingValueTools.getDealType());
        dealType.converterProperty().set(new TransactionTypeChoiceBox());
        //默认选中第一个选项
        dealType.getSelectionModel().selectFirst();
        // 时间范围初始化
        initDatePicker();
        // 表格初始化
        tradeId.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Integer>("tradeId"));
        deposited.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("deposited"));
        expenditure.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("expenditure"));
        transactionType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransactionListEntity, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TransactionListEntity, String> arg0) {
                // return new
                // SimpleStringProperty(arg0.getValue(),"sd",arg0.getValue().getFirstName());
                // //bean, bean的名称，值
                return new SimpleStringProperty(arg0.getValue().getTradeTypeEntity().getTransactionType());
            }
        });
        currencyNumber.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, Double>("currencyNumber"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, String>("transactionAmount"));
        tradeDate.setCellValueFactory(new PropertyValueFactory<TransactionListEntity, String>("tradeDate"));
        operateCol.setCellFactory((col) -> {
            TableCell<TransactionListEntity, String> cell = new TableCell<TransactionListEntity, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        HBox box = new HBox();
                        box.setSpacing(10);
                        // 详情按钮
                        Label moreLabel = new Label();
                        ImageView moreIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/gengduo.png"), 14, 14, true, false));
                        moreLabel.setGraphic(moreIcon);
                        // 编辑按钮
                        Label editLabel = new Label();
                        ImageView editIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/bianji.png"), 14, 14, true, false));
                        editLabel.setGraphic(editIcon);
                        // 删除按钮
                        Label deleteLabel = new Label();
                        ImageView deleteIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/icon-delete.png"), 14, 14, true, false));
                        deleteLabel.setGraphic(deleteIcon);

                        box.getChildren().add(moreLabel);
                        box.getChildren().add(editLabel);
                        box.getChildren().add(deleteLabel);
                        this.setGraphic(box);
                        moreLabel.setOnMouseClicked(me -> {
                            openEditTransactionInfo();
                        });
                        editLabel.setOnMouseClicked(me -> {
                            openEditTransaction();
                        });
                        deleteLabel.setOnMouseClicked(me -> {
                            TransactionListEntity user = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("删除 " + user.getTradeId() + " 的记录");
                            Stage stage = new Stage();
                            NoticeUtils.confirm(
                                    stage,
                                    "确认",
                                    "您确认要删除该记录",
                                    e -> {
                                        stage.close();
                                        data.remove(this.getIndex());
                                    }
                            );
                        });
                    }
                }

            };
            return cell;
        });
        Platform.runLater(() -> handleSearchAction());
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
        Result<Object> requestResult = RequestUrl.getTransactionList(queryCondition);
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
     * 页面跳转 事件
     *
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
     * 打开修改交易明细页面
     */
    private void openEditTransactionInfo() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("control/editTransactionInfo.fxml"));
        Parent target = null;
        try {
            target = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditTransactionInfoControl controller = (EditTransactionInfoControl) loader.getController();
        Scene scene = new Scene(target);
        // 设置背景透明
        scene.setFill(Paint.valueOf("rgba(241,250,250,0.65)"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(GUIApplication.class.getResourceAsStream("/images/system.png")));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("修改流水明细");
        // 资源传递是必须的，否则无法操作执行
        controller.passedResource(stage,target);
        stage.show();
        // 执行页面的二次渲染
        controller.secondRefresh();
    }

    /**
     * 打开修改交易页面
     */
    private void openEditTransaction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("control/editTransaction.fxml"));
        Parent target = null;
        try {
            target = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditTransactionControl controller = (EditTransactionControl) loader.getController();
        Scene scene = new Scene(target);
        // 设置背景透明
        scene.setFill(Paint.valueOf("rgba(241,250,250,0.65)"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(GUIApplication.class.getResourceAsStream("/images/system.png")));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("修改流水");
        // 资源传递是必须的，否则无法操作执行
        controller.passedResource(stage,target);
        stage.show();
        // 执行页面的二次渲染
        controller.secondRefresh();
    }

    /**
     * 搜索事件，触发搜索时，表示页面的筛选条件已经改变，需从第一页开始
     */
    public void handleSearchAction(){
        queryCondition.clear();
        Integer _type = dealType.getSelectionModel().getSelectedItem().getId();
        if (-1 != _type){
            queryCondition.put("tradeType",_type);
        }
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
        // 清空页面表单的筛选条件
        dealType.getSelectionModel().selectFirst();
        beginTime.setValue(LocalDate.now().minusMonths(1));
        endTime.setValue(LocalDate.now());
        handleSearchAction();
    }

    /**
     * 导出
     */
    public void handleOutAction(){
        JSONObject outCondition = new JSONObject();
        Integer _type = dealType.getSelectionModel().getSelectedItem().getId();
        if (-1 != _type){
            outCondition.put("tradeType",_type);
        }
        String _beginTime = beginTime.getValue().format(DateUtils.dateFormat);
        String _endTime = endTime.getValue().format(DateUtils.dateFormat);
        outCondition.put("beginTime",_beginTime);
        outCondition.put("endTime",_endTime);
        //得到用户导出的文件路径
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("报表导出");
        fileChooser.setInitialFileName("财务流水报表.xlsx");
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if(file==null) {
            return;
        }
        // 得到输出文件路径
        String exportFilePath = file.getAbsolutePath().replaceAll(".xlsx", "")+".xlsx";
        RequestUrl.downTransaction(outCondition,exportFilePath);
    }
    
}
