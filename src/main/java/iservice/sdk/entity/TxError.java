package iservice.sdk.entity;

/**
 * @author Yelong
 */
public class TxError {
    private int code;
    private String message;

    public TxError() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TxError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
