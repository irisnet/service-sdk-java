package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/24 11:26 下午
 */
public class TxResultHolder {
    private TxResult TxResult;

    public TxResult getTxResult() {
        return TxResult;
    }

    public void setTxResult(TxResult txResult) {
        TxResult = txResult;
    }

    @Override
    public String toString() {
        return "TxResultHolder{" +
                "TxResult='" + TxResult + '\'' +
                '}';
    }
}
