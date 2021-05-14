package irita.sdk.util;

import com.alibaba.fastjson.JSON;
import irita.sdk.constant.TxStatus;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.base.ResultTx;

public class ResUtils {
    public static ResultTx checkAndConvert(String res) {
        ResultTx resultTx = JSON.parseObject(res, ResultTx.class);

        if (resultTx.getCode() != TxStatus.SUCCESS) {
            throw new IritaSDKException(resultTx.getLog());
        }
        return resultTx;
    }
}
