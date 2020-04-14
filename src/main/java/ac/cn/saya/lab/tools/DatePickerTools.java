package ac.cn.saya.lab.tools;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;

/**
 * @Title: DatePickerTools
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-12 09:48
 * @Description: 时间选择器
 */

public class DatePickerTools {

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
     * 初始化时间选择器
     */
    public void initDatePicker(){
        // 设置开始时间
        beginTime.setValue(LocalDate.now());
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
        // 设置结束时间为开始时间的下一天
        endTime.setValue(beginTime.getValue().plusDays(1));
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
