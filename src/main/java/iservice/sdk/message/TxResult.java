package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/24 4:48 下午
 */
public class TxResult {

    private String height;
    private TxResultInfo result;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public TxResultInfo getResult() {
        return result;
    }

    public void setResult(TxResultInfo result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TxResult{" +
                "height='" + height + '\'' +
                ", result=" + result +
                '}';
    }
}
