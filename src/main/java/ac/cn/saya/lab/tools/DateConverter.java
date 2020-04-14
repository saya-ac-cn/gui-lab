package ac.cn.saya.lab.tools;

import javafx.util.StringConverter;

import java.time.LocalDate;

/**
 * @Title: DateConverter
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/14 0014 15:32
 * @Description: 时间选择器格式转换
 */

public class DateConverter extends StringConverter<LocalDate> {

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return (DateUtils.dateFormat).format(date);
        } else {
            return String.valueOf(LocalDate.now());
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && (string=string.trim()).length()==10 && !string.isEmpty()) {
            return LocalDate.parse(string, DateUtils.dateFormat);
        } else {
            return LocalDate.now();
        }
    }
}
