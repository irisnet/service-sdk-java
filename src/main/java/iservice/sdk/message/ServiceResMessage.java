package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/24 4:45 下午
 */
public class ServiceResMessage extends ServiceBaseMessage {
    private ServiceResResult result;

    public ServiceResResult getResult() {
        return result;
    }

    public void setResult(ServiceResResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ServiceResMessage{" +
                "result=" + result +
                '}';
    }
}
