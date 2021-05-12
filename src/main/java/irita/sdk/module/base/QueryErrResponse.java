package irita.sdk.module.base;

import irita.sdk.exception.QueryException;

public abstract class QueryErrResponse {
    private int code;
    private String message;

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


    public void valid() throws QueryException {
        if (code != 0) {
            throw new QueryException(getMessage());
        }
    }
}
