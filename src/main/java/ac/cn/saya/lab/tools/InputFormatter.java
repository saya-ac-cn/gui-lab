package ac.cn.saya.lab.tools;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: InputFormatter
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 13:50
 * @Description: 文本框内容验证
 */

public class InputFormatter {

    public static boolean isNumber(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d{0,7})|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isOverLenth(String str,int length){
        if(str.length() > length){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 通用的小数验证
     * @return
     */
    public static TextFormatter<Double> doubleFormatte(){
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (isNumber(c.getControlNewText())) {
                return c ;
            } else {
                return null ;
            }
        };
        return new TextFormatter<>(filter);
    }

    public static TextFormatter<String> stringLengthFormatte(){
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (isOverLenth(c.getControlNewText(),15)) {
                return c ;
            } else {
                return null ;
            }
        };
        return new TextFormatter<>(filter);
    }

    public static void main(String[] args) {
        System.out.println(isNumber("1."));
    }

}
