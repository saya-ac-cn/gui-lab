package ac.cn.saya.lab.tools;

import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.entity.DealDirection;
import ac.cn.saya.lab.entity.TransactionAmountEntity;
import ac.cn.saya.lab.entity.TransactionListEntity;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: SingValueTools
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 20:07
 * @Description:
 */

public class SingValueTools {

    public static List<TransactionTypeEntity> getDealType(){
        List<TransactionTypeEntity> list = new ArrayList<>();
        list.add(new TransactionTypeEntity(-1,"请选择"));
        // 向后台发起获取交易类别
        Result<Object> requestResult = RequestUrl.getTransactionType();
        if (ResultUtil.checkSuccess(requestResult)){
            JSONArray requestData = (JSONArray)requestResult.getData();
            list.addAll(JSONObject.parseArray(requestData.toJSONString(), TransactionTypeEntity.class));
        }
        return list;
    }

    public static List<TransactionAmountEntity> getTradeAmount(){
        List<TransactionAmountEntity> list = new ArrayList<>();
        list.add(new TransactionAmountEntity(-1,"请选择"));
        // 向后台发起获取交易类别
        Result<Object> requestResult = RequestUrl.getTransactionAmount();
        if (ResultUtil.checkSuccess(requestResult)){
            JSONArray requestData = (JSONArray)requestResult.getData();
            list.addAll(JSONObject.parseArray(requestData.toJSONString(), TransactionAmountEntity.class));
        }
        return list;
    }

    public static List<DealDirection> getDealDirection(){
        List<DealDirection> list = new ArrayList<>();
        list.add(new DealDirection(1,"存入"));
        list.add(new DealDirection(2,"取出"));
        return list;
    }
    public static String cellSerialDealDirection(int flog){
        if (2 == flog){
            return "取出";
        }else{
            return "存入";
        }
    }

}
