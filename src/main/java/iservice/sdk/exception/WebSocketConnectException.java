package iservice.sdk.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/21 4:01 上午
 */
public class WebSocketConnectException extends RuntimeException{
    public WebSocketConnectException() {
    }

    public WebSocketConnectException(String message) {
        super(message);
    }

    public WebSocketConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebSocketConnectException(Throwable cause) {
        super(cause);
    }
}
