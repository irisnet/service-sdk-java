package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/23 3:00 下午
 */
public class ServiceReqMessage extends ServiceBaseMessage {

    private ServiceReqResult result;

    public ServiceReqResult getResult() {
        return result;
    }

    public void setResult(ServiceReqResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ServiceReqMessage{" +
                "result=" + result +
                '}';
    }
}
