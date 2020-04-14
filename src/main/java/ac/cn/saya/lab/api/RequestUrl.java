package ac.cn.saya.lab.api;

import ac.cn.saya.lab.tools.HttpRequestUtils;
import ac.cn.saya.lab.tools.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * @Title: RequestUrl
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 10:32
 * @Description:
 */

public class RequestUrl {

    private static final String prefixUrh= "http://127.0.0.1:8080";

    /**
     * 检查请求是否成功
     * @param parmer
     * @return
     */
    public static boolean checkSuccess(JSONObject parmer){
        if (null == parmer || StringUtils.isBlank(parmer.getString("code"))){
            return false;
        }
        return true;
    }

    /**
     * 登录接口
     * @param parmar
     * @return
     */
    public static JSONObject login(JSONObject parmar){
        JSONObject result = null;
        try {
            String resultStr = HttpRequestUtils.httpPost(prefixUrh+"/backend/login", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            System.out.println(resultStr);
            result = JSON.parseObject(resultStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
