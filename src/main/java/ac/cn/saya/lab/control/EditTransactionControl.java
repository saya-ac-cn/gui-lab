package ac.cn.saya.lab.control;

import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.controller.TransactionViewController;
import ac.cn.saya.lab.entity.TransactionListEntity;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import ac.cn.saya.lab.tools.*;
import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @Title: EditTransactionControl
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/14 0014 14:59
 * @Description:
 */

public class EditTransactionControl implements Initializable {

    // 定义偏移量，用于处理窗口移动
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * 交易类别
     */
    @FXML
    public ChoiceBox<TransactionTypeEntity> dealType;

    /**
     * 交易摘要
     */
    @FXML
    public TextField summaryText;

    /**
     * 交易时间
     */
    @FXML
    public DatePicker tradeDate;

    @FXML
    public Label errorLabel;

    /**
     * 存储外部传递的资源
     */
    private Stage _stage;

    private Parent _root;

    private TransactionListEntity _line;

    private TransactionViewController _paren;

    /**
     * 对外接受资源
     *
     * @param stage
     */
    public void passedResource(Stage stage, Parent root, TransactionListEntity line,TransactionViewController paren) {
        _stage = stage;
        _root = root;
        _line = line;
        _paren = paren;
    }

    /**
     * 页面二次刷新，由外部触发，
     */
    public void secondRefresh() {
        // 数据回显
        dealType.getSelectionModel().select(_line.getTradeTypeEntity());
        tradeDate.setValue(LocalDate.parse(_line.getTradeDate(),DateUtils.dateFormat));
        summaryText.setText(_line.getTransactionAmount());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 页面选择器初始化
        dealType.getItems().addAll(SingValueTools.getDealType());
        dealType.converterProperty().set(new TransactionTypeChoiceBox());
        summaryText.setTextFormatter(InputFormatter.stringLengthFormatte());
        // 设置开始时间
        tradeDate.setValue(LocalDate.now());
        tradeDate.setConverter(new DateConverter());
        StringConverter<LocalDate> converter = new DateConverter();
        tradeDate.setConverter(converter);
    }

    /**
     * 关闭窗口
     * @param event
     */
    public void handleClosePageAction(MouseEvent event) {
        // 调用父容器传进来的stage进行操作
        _stage.close();
    }

    /**
     * 提交保存
     * @param event
     */
    public void handleSubmitPageAction(MouseEvent event){
        // 用户选中的支付方式
        Integer _dealType = dealType.getSelectionModel().getSelectedItem().getId();
        if (-1 == _dealType){
            errorLabel.setText("请选择交易方式");
            return;
        }
        String summary = summaryText.getText();
        if (StringUtils.isBlank(summary) || (summary = summary.trim()).length() <0){
            errorLabel.setText("请填写摘要");
            return;
        }
        JSONObject para = new JSONObject();
        para.put("tradeId",_line.getTradeId());
        para.put("tradeType",_dealType);
        para.put("tradeDate",tradeDate.getValue().format(DateUtils.dateFormat));
        para.put("transactionAmount",summary);
        Result<Object> requestResult = RequestUrl.updateTransaction(para);
        if (ResultUtil.checkSuccess(requestResult)) {
            // 关闭当前页面
            _stage.close();
            // 刷新父页面
            _paren.getData(null);
            NoticeUtils.show("处理结果", "修改成功");
        }else {
            NoticeUtils.show("处理结果",requestResult.getMsg());
        }
    }

}
