package iservice.sdk.entity;

/**
 * @author Yelong
 */
public abstract class BaseServiceResponse<T> extends BaseRequest<T> {

    protected BaseServiceResponse() {
    }

    @Override
    public void validateParams() throws IllegalArgumentException {
        super.validateParams();
    }
}
