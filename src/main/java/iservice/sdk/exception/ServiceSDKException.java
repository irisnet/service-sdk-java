package iservice.sdk.exception;

/**
 * @author Yelong
 * @date 2020/9/18
 */
public class ServiceSDKException extends RuntimeException {

    public ServiceSDKException() {
        super();
    }

    public ServiceSDKException(String message) {
        super(message);
    }
}
