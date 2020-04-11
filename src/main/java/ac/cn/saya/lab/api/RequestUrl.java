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

    /**
     * 检查请求是否成功
     * @param parmer
     * @return
     */
    public static boolean checkSuccess(JSONObject parmer){
        if (null == parmer || StringUtils.isBlank(parmer.getString("resultCode"))){
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
            String resultStr = HttpRequestUtils.httpPost("http://172.20.11.66:8055/erp/account/login", null, parmar, 6000, false, HttpRequestUtils.getClientContext());
            System.out.println(resultStr);
            result = JSON.parseObject(resultStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
