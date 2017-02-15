package com.wx.rxretrofitlibrary.exception;

//添加自定义错误
public class HttpRunException extends RuntimeException{

    private int resultCode;

    public HttpRunException(int resultCode) {
        super(getApiExceptionMessage(resultCode));
        this.resultCode = resultCode;
    }

    public HttpRunException(String detailMessage) {
        super(detailMessage);
    }

    public int getResultCode() {
        return resultCode;
    }

    private static String getApiExceptionMessage(int code) {
        switch (code) {
            case ErrorCode.DATANULL_ERROR_CODE:
                return ErrorCode.DATANULL_ERROR_MSG;
        }
        return ErrorCode.RUNTIME_ERROR_MSG;
    }

}
