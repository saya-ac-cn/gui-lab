package ac.cn.saya.lab.assemb;

import ac.cn.saya.lab.tools.DateConverter;
import ac.cn.saya.lab.tools.DatePickerTools;
import ac.cn.saya.lab.tools.DateUtils;
import ac.cn.saya.lab.tools.PagingTools;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;

/**
 * PagingTools增强类,加入日期选择
 * @Title: AdvisorPagingAndDate
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-12 10:14
 * @Description: 由于java不允许多继承，接口内的Fxml属性不能直接初始化，只能采用这种方式，大致膨胀2倍的代码
 */

public class AdvisorPagingAndDate extends PagingTools {

    /**
     * 开始时间
     */
    @FXML
    public DatePicker beginTime;

    /**
     * 结束时间
     */
    @FXML
    public DatePicker endTime;

    /**
     * 查询一个月的
     * 初始化时间选择器
     */
    public void initDatePicker(){
        // 设置开始时间上一个月
        beginTime.setValue(LocalDate.now().minusMonths(1));
        final Callback<DatePicker, DateCell> endDatePickerFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        beginTime.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFCCCC;");
                                }
                            }
                        };
                    }
                };
        // 为结束时间绑定事件，使之不能早于开始时间
        endTime.setDayCellFactory(endDatePickerFactory);
        // 设置结束时间为当前时间
        endTime.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> firstDatePickerFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isAfter(
                                        endTime.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFCCCC;");
                                }
                            }
                        };
                    }
                };
        // 为开始时间绑定事件，使之不能晚于结束时间
        beginTime.setDayCellFactory(firstDatePickerFactory);
        StringConverter<LocalDate> converter = new DateConverter();
        beginTime.setConverter(converter);
        endTime.setConverter(converter);
    }

}
