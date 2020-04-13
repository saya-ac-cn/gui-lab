package ac.cn.saya.lab.tools;

/**
 * @Title: StringUtils
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 09:38
 * @Description:
 */

public class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 提取按钮字符串中的数字
     * @param str
     * @return
     */
    public static int getButtonIndex(String str){
        int index;
        if (isBlank(str)){
            return -1;
        }
        try {
            String[] line = str.split(" ");
            index = Integer.parseInt(line[1]);
        } catch (NumberFormatException e) {
            index = -1;
        }
        return index;
    }

}
