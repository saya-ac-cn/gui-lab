package ac.cn.saya.lab.tools;

import ac.cn.saya.lab.entity.TransactionTypeEntity;

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
        list.add(new TransactionTypeEntity(0,"支付宝"));
        list.add(new TransactionTypeEntity(2,"微信"));
        list.add(new TransactionTypeEntity(3,"信用卡"));
        return list;
    }

}
