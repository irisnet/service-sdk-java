package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/24 8:08 下午
 */
public class BusinessResponseResult {

    private Integer SUCCESS_CODE = 200;

    Integer code;
    String message;

    public boolean isSuccess(){
        return SUCCESS_CODE.equals(code);
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
        return "BusinessResponseResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
