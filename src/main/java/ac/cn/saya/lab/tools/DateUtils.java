package ac.cn.saya.lab.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期时间工具类（jdk1.8）
 * @Title: DateUtils
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 23:56
 * @Description:
 * https://blog.csdn.net/qq32933432/article/details/87974071
 */

public class DateUtils {

    /**
     * 文件上传及目录创建生成时间
     */
    public static DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 日期格式
     */
    public final static DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * 日期格式
     */
    public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 日期时间格式
     */
    public final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取当前日期时间
     * @param dateFormat 时间格式 eg："yyyy-MM-dd HH:mm:ss"
     * @return 2019-10-03 00:05:32
     */
    public static String getCurrentDateTime(DateTimeFormatter dateFormat) {
        return LocalDateTime.now().format(dateFormat);
    }

    /**
     * 获取当前年月
     * @return 2019-10-03 00:05:32
     */
    public static String getCurrentMonth() {
        return LocalDateTime.now().format(monthFormat);
    }

    /**
     * 获取当前号数
     * @return
     */
    public static String getCurrentDay() {
        LocalDate date = LocalDate.now();
        return String.valueOf(date.getDayOfMonth());
    }


    /**
     * @描述 获取今天星期几
     * @参数 date
     * @返回值 星期日i==1，星期六i==7
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static int getDayOfTheWeek(LocalDate date) {
        ///String[][] strArray = {{"SUNDAY", "日"},{"MONDAY", "一"}, {"TUESDAY", "二"}, {"WEDNESDAY", "三"}, {"THURSDAY", "四"}, {"FRIDAY", "五"}, {"SATURDAY", "六"}};
        String[][] strArray = {{"SUNDAY", "1"},{"MONDAY", "2"}, {"TUESDAY", "3"}, {"WEDNESDAY", "4"}, {"THURSDAY", "5"}, {"FRIDAY", "6"}, {"SATURDAY", "7"}};
        String k = String.valueOf(date.getDayOfWeek());
        System.out.println("k:"+k);
        //获取行数
        for (int i = 0; i < strArray.length; i++) {
            if (k.equals(strArray[i][0])) {
                k = strArray[i][1];
                break;
            }
        }
        return Integer.valueOf(k);
    }

    /**
     * @描述 获取该月的天数
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static int getLengthOfMonth(String date){
        LocalDate localDate = LocalDate.parse(date, DateUtils.dateFormat);
        return localDate.lengthOfMonth();
    }

    /**
     * @描述 获取该月第一天是星期几
     * @参数  date eg：2019-06-10
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static int getFirstDayWeek(String date){
        LocalDate localDate = LocalDate.parse(date, DateUtils.dateFormat);
        // 构造本月第一天
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return DateUtils.getDayOfTheWeek(firstDay);
    }

    /**
     * @描述 获取指定月份的第一天
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static String getFirstDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, DateUtils.dateFormat);
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return firstDay.format(DateUtils.dateFormat);
    }

    /**
     * @描述 获取指定月份的最后一天
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static String getLastDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, DateUtils.dateFormat);
        LocalDate firstDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return firstDay.format(DateUtils.dateFormat);
    }

    /**
     * 根据当前小时，返回时段
     * @return
     */
    public static String getNowHourHello() {
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        if (hour >= 0 && hour <= 6) {
            return "凌晨";
        }
        if (hour > 6 && hour <= 12) {
            return "上午";
        }
        if (hour > 12 && hour <= 13) {
            return "中午";
        }
        if (hour > 13 && hour <= 18) {
            return "下午";
        }
        if (hour > 18 && hour <= 24) {
            return "晚上";
        }
        return "您好";
    }

    /**
     * 根据小时，得到问候词
     * @return
     */
    public static String getGreetText(){
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        String greetText = "好久不见，甚是想念，记得爱护自己！";
        if(hour >= 0 && hour < 7){
            greetText = "天还没亮，夜猫子，要注意身体哦！";
        }else if(hour>=7 && hour<12){
            greetText = "上午好！又是元气满满的一天，奥利给！";
        }else if(hour >= 12 && hour < 14){
            greetText = "中午好！吃完饭记得午休哦！";
        }else if(hour >= 14 && hour < 18){
            greetText = "下午茶的时间到了，休息一下吧！";
        }else if(hour >= 18 && hour < 22){
            greetText = "晚上到了，多陪陪家人吧！";
        }else if(hour >= 22 && hour < 24){
            greetText = "很晚了哦，注意休息呀！";
        }
        return greetText;
    }

    /**
     * 返回最近6个月的日期，不含当前月
     * @return
     */
    public static String [] getHalfYearData(){
        LocalDate now = LocalDate.now();
        String[] dateArray = new String[6];
        for (int i = 1; i <= 6; i++){
            now = now.minusMonths(1);
            dateArray[6-i] = now.format(monthFormat);
        }
        return dateArray;
    }

    public static void main(String[] args) {
        String datetime = DateUtils.getCurrentDateTime(DateUtils.dateTimeFormat);
        System.out.println("now-date:"+datetime);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth("2019-10-10");
        System.out.println("firstDayOfMonth:"+firstDayOfMonth);
        String lastDayOfMonth = DateUtils.getLastDayOfMonth("2019-10-10");
        System.out.println("lastDayOfMonth:"+lastDayOfMonth);
        System.out.println("本月天数:"+DateUtils.getLengthOfMonth("2019-10-20"));
        System.out.println("本月第一天是:"+getFirstDayWeek("2019-10-20"));
        String[] halfYearData = getHalfYearData();
        for (String item:halfYearData) {
            System.out.println(item);
        }
    }

}
