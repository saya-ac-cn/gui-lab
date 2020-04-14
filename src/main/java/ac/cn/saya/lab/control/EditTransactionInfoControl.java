package ac.cn.saya.lab.control;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.entity.DealDirection;
import ac.cn.saya.lab.entity.TransactionInfoEntity;
import ac.cn.saya.lab.tools.InputFormatter;
import ac.cn.saya.lab.tools.NoticeUtils;
import ac.cn.saya.lab.tools.SingValueTools;
import javafx.application.Platform;
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
     * 底部关闭，提交按钮
     */
    @FXML
    public Button closeButton, submitButton;

    /**
     * 存储外部传递的资源
     */
    private Stage _stage;

    private Parent _root;

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
    public TableColumn<TransactionInfoEntity, Integer> flog;

    @FXML
    public TableColumn<TransactionInfoEntity, Double> currencyNumber;

    @FXML
    public TableColumn<TransactionInfoEntity, String> currencyDetails;

    @FXML
    private TableColumn<TransactionInfoEntity, String> operateCol;

    /**
     * 对外接受资源
     *
     * @param stage
     */
    public void passedResource(Stage stage, Parent root) {
        _stage = stage;
        _root = root;
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
        setDisable(true);
        // 对于部分输入框，预先设置初值
        addChargeField.setText("0.0");
        editChargeField.setText("0.0");
        addDescField.setText("详情");
        editDescField.setText("详情");
        // 表格格式初始化
        flog.setCellValueFactory(new PropertyValueFactory<TransactionInfoEntity, Integer>("flog"));
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
                            if (thisEditLine != null && _line.getId() == thisEditLine.getId()){
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
                                        stage.close();
                                        data.remove(this.getIndex());
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
                            setDisable(false);
                        });
                    }
                }

            };
            return cell;
        });
        Platform.runLater(() -> getData());

    }

    /**
     * 页面二次刷新，由外部触发，
     */
    public void secondRefresh() {
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
        // 数据查询
        data.clear();
        data.add(new TransactionInfoEntity(1, 1, 1.1, "测试"));
        data.add(new TransactionInfoEntity(2, 2, 1.2, "测试"));
        data.add(new TransactionInfoEntity(3, 3, 1.3, "测试"));
        data.add(new TransactionInfoEntity(4, 1, 1.4, "测试"));
        data.add(new TransactionInfoEntity(5, 2, 1.5, "测试"));
        data.add(new TransactionInfoEntity(6, 3, 1.6, "测试"));
        data.add(new TransactionInfoEntity(7, 1, 1.7, "测试"));
        data.add(new TransactionInfoEntity(8, 2, 1.8, "测试"));
        data.add(new TransactionInfoEntity(9, 3, 1.9, "测试"));
        data.add(new TransactionInfoEntity(10, 1, 2.0, "测试"));
        dataTableView.setItems(data);
    }

    public void handleClosePageAction(MouseEvent event) {
        // 调用父容器传进来的stage进行操作
        _stage.close();
    }

    /**
     * 禁用启用编辑组件
     * @param value
     */
    private void setDisable(boolean value){
        editDealDirection.setDisable(value);
        editChargeField.setDisable(value);
        editDescField.setDisable(value);
        editButton.setDisable(value);
    }

}
