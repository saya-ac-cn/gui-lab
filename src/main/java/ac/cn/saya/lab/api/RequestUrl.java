package ac.cn.saya.lab.api;

import ac.cn.saya.lab.tools.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * @Title: RequestUrl
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author saya
 * @Date: 2020/3/31 0031 10:32
 * @Description:
 */

public class RequestUrl {

    public static final String prefixUrh= "http://127.0.0.1:8080";
    //public static final String prefixUrh= "http://lab.saya.ac.cn";

    /**
     * 检查请求是否成功
     * @param parmer
     * @return
     */
    public static boolean checkSuccess(JSONObject parmer){
        if (null != parmer || !StringUtils.isBlank(parmer.getString("code")) || parmer.getInteger("code") == 0){
            return true;
        }
        return false;
    }

    /**
     * 登录接口
     * @param parmar
     * @return
     */
    public static Result<Object> login(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpPost(prefixUrh+"/backend/login/swing", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 获取财政数据
     * @param parmar
     * @return
     */
    public static Result<Object> getTransactionList(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/transaction", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 获取交易类别
     * @return
     */
    public static Result<Object> getTransactionType(){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/transactionType", null, null, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 导出流水
     * @param parmar
     * @param savePath
     * @return
     */
    public static boolean downTransaction(JSONObject parmar,String savePath){
        try {
            return HttpRequestUtils.httpDownload(prefixUrh+"/backend/api/financial/outTransactionListExcel", null, parmar,savePath, 60000, false, HttpRequestUtils.getClientContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 按天统计流水
     * @param parmar
     * @return
     */
    public static Result<Object> totalTransactionForDay(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/totalTransactionForDay", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 导出按天统计的报表
     * @param parmar
     * @param savePath
     * @return
     */
    public static boolean downTransactionForDayExcel(JSONObject parmar,String savePath){
        try {
            return HttpRequestUtils.httpDownload(prefixUrh+"/backend/api/financial/outTransactionForDayExcel", null, parmar,savePath, 60000, false, HttpRequestUtils.getClientContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 按月统计流水
     * @param parmar
     * @return
     */
    public static Result<Object> totalTransactionForMonth(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/totalTransactionForMonth", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 导出按月统计的报表
     * @param savePath
     * @return
     */
    public static boolean outTransactionForMonthExcel(String savePath){
        try {
            return HttpRequestUtils.httpDownload(prefixUrh+"/backend/api/financial/outTransactionForMonthExcel", null, null,savePath, 60000, false, HttpRequestUtils.getClientContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 按年统计流水
     * @param parmar
     * @return
     */
    public static Result<Object> totalTransactionForYear(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/totalTransactionForYear", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 导出按年统计的报表
     * @param savePath
     * @return
     */
    public static boolean outTransactionForYearExcel(String savePath){
        try {
            return HttpRequestUtils.httpDownload(prefixUrh+"/backend/api/financial/outTransactionForYearExcel", null, null,savePath, 60000, false, HttpRequestUtils.getClientContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 财政申报
     * @param parmar
     * @return
     */
    public static Result<Object> applyTransaction(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpPost(prefixUrh+"/backend/api/financial/insertTransaction", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }


    /**
     * 修改交易流水
     * @param parmar
     * @return
     */
    public static Result<Object> updateTransaction(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpPut(prefixUrh+"/backend/api/financial/updateTransaction", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 删除交易流水
     * @param parmar
     * @return
     */
    public static Result<Object> deleteTransaction(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpDelete(prefixUrh+"/backend/api/financial/deleteTransaction", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 获取流水明细
     * @param parmar
     * @return
     */
    public static Result<Object> getTransactionInfo(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/financial/transactionInfo", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 添加流水明细
     * @param parmar
     * @return
     */
    public static Result<Object> insertTransactioninfo(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpPost(prefixUrh+"/backend/api/financial/insertTransactioninfo", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 修改流水明细
     * @param parmar
     * @return
     */
    public static Result<Object> updateTransactioninfo(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpPut(prefixUrh+"/backend/api/financial/updateTransactioninfo", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 删除流水明细
     * @param parmar
     * @return
     */
    public static Result<Object> deleteTransactioninfo(JSONObject parmar){
        try {
            String resultStr = HttpRequestUtils.httpDelete(prefixUrh+"/backend/api/financial/deleteTransactioninfo", null, parmar, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

    /**
     * 获取仪表盘数据
     * @return
     */
    public static Result<Object> getDoshBoard(){
        try {
            String resultStr = HttpRequestUtils.httpGet(prefixUrh+"/backend/api/set/dashBoard", null, null, 60000, false, HttpRequestUtils.getClientContext());
            return JSON.parseObject(resultStr, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.ERROP);
    }

}
