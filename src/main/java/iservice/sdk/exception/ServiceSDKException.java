package iservice.sdk.exception;

/**
 * @author Yelong
 */
public class ServiceSDKException extends RuntimeException {

    private static final long serialVersionUID = 7177887186522102117L;

    public ServiceSDKException() {
    }

    public ServiceSDKException(String message) {
        super(message);
    }

    public ServiceSDKException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceSDKException(Throwable cause) {
        super(cause);
    }

    public ServiceSDKException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
