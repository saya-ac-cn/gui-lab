package ac.cn.saya.lab.control;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.controller.TransactionViewController;
import ac.cn.saya.lab.entity.DealDirection;
import ac.cn.saya.lab.entity.TransactionInfoEntity;
import ac.cn.saya.lab.tools.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Title: EditTransactionInfoControl
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 17:42
 * @Description: 修改交易明细
 */

public class EditTransactionInfoControl implements Initializable {

    // 定义偏移量，用于处理窗口移动
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * 添加组件（出入类型）
     */
    @FXML
    public ChoiceBox<DealDirection> addDealDirection;

    /**
     * 添加组件（金额）
     */
    @FXML
    public TextField addChargeField;

    /**
     * 添加组件（详情）
     */
    @FXML
    public TextField addDescField;

    /**
     * 修改组件（出入类型）
     */
    @FXML
    public ChoiceBox<DealDirection> editDealDirection;

    /**
     * 修改组件（金额）
     */
    @FXML
    public TextField editChargeField;

    /**
     * 修改组件（详情）
     */
    @FXML
    public TextField editDescField;

    /**
     * 修改组件（编辑按钮）
     */
    public Button editButton;


    /**
     * 存储外部传递的资源
     */
    private Stage _stage;

    private Parent _root;

    private int _tradeId;

    private TransactionViewController _parent;

    /**
     * 表格数据部分
     */
    /**
     * 表格
     */
    @FXML
    public TableView<TransactionInfoEntity> dataTableView;

    /**
     * 数据
     */
    private ObservableList<TransactionInfoEntity> data = FXCollections.observableArrayList();

    /**
     * 当前修改行的数据，默认为空，点击编辑时赋予行数据
     * 提交编辑后，清空数据
     * 当这行数据被删除时，也要清空
     */
    private TransactionInfoEntity thisEditLine;

    @FXML
    public TableColumn<TransactionInfoEntity, String> flog;

    @FXML
    public TableColumn<TransactionInfoEntity, Double> currencyNumber;

    @FXML
    public TableColumn<TransactionInfoEntity, String> currencyDetails;

    @FXML
    private TableColumn<TransactionInfoEntity, String> operateCol;

    /**
     * 查询条件
     */
    private JSONObject queryCondition = new JSONObject();

    /**
     * 本页面执行添加、修改和删除次数，关闭时，判断是否需要刷新父页面
     */
    private int renewNum = 0;

    /**
     * 对外接受资源
     *
     * @param stage
     */
    public void passedResource(Stage stage, Parent root, int tradeId, TransactionViewController parent) {
        _stage = stage;
        _root = root;
        _tradeId = tradeId;
        _parent = parent;
        queryCondition.put("nowPage",1);
        queryCondition.put("pageSize",20);
        queryCondition.put("tradeId",tradeId);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 下拉选框提前初始化
        addDealDirection.getItems().addAll(SingValueTools.getDealDirection());
        addDealDirection.getSelectionModel().selectFirst();
        editDealDirection.getItems().addAll(SingValueTools.getDealDirection());
        editDealDirection.getSelectionModel().selectFirst();
        // 对于输入框，提前设置好校验规则
        addChargeField.setTextFormatter(InputFormatter.doubleFormatte());
        editChargeField.setTextFormatter(InputFormatter.doubleFormatte());
        addDescField.setTextFormatter(InputFormatter.stringLengthFormatte());
        editDescField.setTextFormatter(InputFormatter.stringLengthFormatte());
        // 初次进入时，修改功能禁用，只有触发单机表格行后才触发修改，修改成功后，再次进入禁用状态
        setDisableInput(true);
        // 对于部分输入框，预先设置初值
        addChargeField.setText("0.0");
        editChargeField.setText("0.0");
        addDescField.setText(null);
        editDescField.setText(null);
        // 表格格式初始化
        flog.setCellValueFactory((cell)->{
            /// SimpleStringProperty(arg0.getValue(),"sd",arg0.getValue().getFirstName());
            return new SimpleStringProperty(SingValueTools.cellSerialDealDirection(cell.getValue().getFlog()));
        });
        currencyNumber.setCellValueFactory(new PropertyValueFactory<TransactionInfoEntity, Double>("currencyNumber"));
        currencyDetails.setCellValueFactory(new PropertyValueFactory<TransactionInfoEntity, String>("currencyDetails"));
        // 表格事件的初始化
        operateCol.setCellFactory((col) -> {
            TableCell<TransactionInfoEntity, String> cell = new TableCell<TransactionInfoEntity, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        HBox box = new HBox();
                        box.setSpacing(10);
                        // 编辑按钮
                        Label editLabel = new Label();
                        ImageView editIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/bianji.png"), 14, 14, true, false));
                        editLabel.setGraphic(editIcon);
                        // 删除按钮
                        Label deleteLabel = new Label();
                        ImageView deleteIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/icon-delete.png"), 14, 14, true, false));
                        deleteLabel.setGraphic(deleteIcon);

                        box.getChildren().add(editLabel);
                        box.getChildren().add(deleteLabel);
                        this.setGraphic(box);
                        deleteLabel.setOnMouseClicked(me -> {
                            TransactionInfoEntity _line = this.getTableView().getItems().get(this.getIndex());
                            // 移除前判断删除的行数据是否为当前编辑行数据
                            if (thisEditLine != null && (_line.getId()).equals(thisEditLine.getId())){
                                editDealDirection.getSelectionModel().selectFirst();
                                editChargeField.setText("0.0");
                                editDescField.setText("详情");
                                // 此时底部的编辑框禁用
                                setDisable(true);
                            }
                            Stage stage = new Stage();
                            NoticeUtils.confirm(
                                    stage,
                                    "确认",
                                    "您确认要删除该记录",
                                    e -> {
                                        //data.remove(this.getIndex());
                                        JSONObject para = new JSONObject();
                                        para.put("id",_line.getId());
                                        RequestUrl.deleteTransactioninfo(para);
                                        getData();
                                        renewNum++;
                                        stage.close();
                                    }
                            );

                        });
                        editLabel.setOnMouseClicked(me -> {
                            thisEditLine = this.getTableView().getItems().get(this.getIndex());
                            // 赋予初始值
                            editDealDirection.getSelectionModel().selectFirst();
                            editChargeField.setText(String.valueOf(thisEditLine.getCurrencyNumber()));
                            editDescField.setText(thisEditLine.getCurrencyDetails());
                            // 此时底部的编辑框可以编辑
                            setDisableInput(false);
                        });
                    }
                }

            };
            return cell;
        });
    }

    /**
     * 页面二次刷新，由外部触发，
     */
    public void secondRefresh() {
        getData();
        _root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        _root.setOnMouseDragged(event -> {
            _stage.setX(event.getScreenX() - xOffset);
            _stage.setY(event.getScreenY() - yOffset);
        });
        // 防止在页面切换后，窗口不居中
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        _stage.setX((screenBounds.getWidth() - _stage.getWidth()) / 2);
        _stage.setY((screenBounds.getHeight() - _stage.getHeight()) / 2);
    }

    /**
     * 渲染表格
     */
    private void getData() {
        Result<Object> requestResult = RequestUrl.getTransactionInfo(queryCondition);
        if (ResultUtil.checkSuccess(requestResult)){
            // 请求成功
            JSONObject requestData = (JSONObject)requestResult.getData();
            JSONArray grid = (JSONArray) requestData.getOrDefault("grid",null);
            List<TransactionInfoEntity> gridList = JSONObject.parseArray(grid.toJSONString(), TransactionInfoEntity.class);
            data.clear();
            data.addAll(gridList);
            dataTableView.setItems(data);
        }
    }

    public void handleClosePageAction(MouseEvent event) {
        // 调用父容器传进来的stage进行操作
        _stage.close();
        if (0 != renewNum){
            _parent.getData(null);
        }
    }

    /**
     * 禁用启用编辑组件
     * @param value
     */
    private void setDisableInput(boolean value){
        editDealDirection.setDisable(value);
        editChargeField.setDisable(value);
        editDescField.setDisable(value);
        editButton.setDisable(value);
    }

    public void handleAddInfoAction(){
        Integer flog = addDealDirection.getSelectionModel().getSelectedItem().getType();
        String currencyNumber = addChargeField.getText();
        if (!InputFormatter.isNumber(currencyNumber)){
            NoticeUtils.show("处理结果","您填写的金额不合法");
            return;
        }
        // 校验描述是否合法
        String currencyDetails = addDescField.getText();
        if (StringUtils.isBlank(currencyDetails)){
            NoticeUtils.show("处理结果","请填写正确描述");
            return;
        }
        JSONObject parmar = new JSONObject();
        parmar.put("flog",flog);
        parmar.put("currencyNumber",currencyNumber);
        parmar.put("currencyDetails",currencyDetails);
        parmar.put("tradeId",_tradeId);
        Result<Object> requestResult = RequestUrl.insertTransactioninfo(parmar);
        if (ResultUtil.checkSuccess(requestResult)){
            renewNum++;
            getData();
            thisEditLine = null;
            NoticeUtils.show("处理结果","添加成功");
            // 还原表单
            addChargeField.setText("0.0");
            addDescField.setText(null);
        }else {
            NoticeUtils.show("处理结果",requestResult.getMsg());
        }
    }

    public void handleEditInfoAction(){
        Integer flog = editDealDirection.getSelectionModel().getSelectedItem().getType();
        String currencyNumber = editChargeField.getText();
        if (!InputFormatter.isNumber(currencyNumber)){
            NoticeUtils.show("处理结果","您填写的金额不合法");
            return;
        }
        // 校验描述是否合法
        String currencyDetails = editDescField.getText();
        if (StringUtils.isBlank(currencyDetails)){
            NoticeUtils.show("处理结果","请填写正确描述");
            return;
        }
        JSONObject parmar = new JSONObject();
        parmar.put("flog",flog);
        parmar.put("currencyNumber",currencyNumber);
        parmar.put("currencyDetails",currencyDetails);
        parmar.put("tradeId",_tradeId);
        parmar.put("id",thisEditLine.getId());
        Result<Object> requestResult = RequestUrl.updateTransactioninfo(parmar);
        if (ResultUtil.checkSuccess(requestResult)){
            renewNum++;
            getData();
            thisEditLine = null;
            NoticeUtils.show("处理结果","修改成功");
            // 还原表单
            editChargeField.setText("0.0");
            editDescField.setText(null);
            setDisableInput(true);
        }else {
            NoticeUtils.show("处理结果",requestResult.getMsg());
        }
    }

}
