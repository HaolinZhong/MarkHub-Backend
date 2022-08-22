package hz.blog.markhub.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ServiceExceptionEnum implements CommonError{
    // unknown error
    UNKNOWN_ERROR(1000, "Service failed due to internal error"),

    // login related
    AUTHENTICATION_FAILED(2000, "Authentication Failed."),
    INVALID_PARAMETER(2001, "Input parameters are invalid."),
    CREDENTIALS_EXPIRED(2002, "Token has already expired. Please login again"),

    // info related
    ITEM_NOT_FOUND(4004, "Item not found")

    ;

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
