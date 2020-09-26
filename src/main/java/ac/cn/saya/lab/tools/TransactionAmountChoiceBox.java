package ac.cn.saya.lab.tools;

import ac.cn.saya.lab.entity.TransactionAmountEntity;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import javafx.util.StringConverter;

/**
 * @Title: TransactionTypeChoiceBox
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-18 14:02
 * @Description: TransactionType 交易类别 ChoiceBox中绑定复合对象
 */

public class TransactionAmountChoiceBox extends StringConverter<TransactionAmountEntity> {

    /**
     * Converts the object provided into its string form.
     * Format of the returned string is defined by the specific converter.
     *
     * @param object
     * @return a string representation of the object passed in.
     */
    @Override
    public String toString(TransactionAmountEntity object) {
        return object.getTag();
    }

    /**
     * Converts the string provided into an object defined by the specific converter.
     * Format of the string and type of the resulting object is defined by the specific converter.
     *
     * @param string
     * @return an object representation of the string passed in.
     */
    @Override
    public TransactionAmountEntity fromString(String string) {
        return null;
    }
}
