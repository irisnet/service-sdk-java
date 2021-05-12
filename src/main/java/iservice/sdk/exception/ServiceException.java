package iservice.sdk.exception;

/**
 * @author : ori
 * @date : 2020/9/24 8:11 下午
 */
public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
