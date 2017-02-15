package com.wx.rxretrofitlibrary.exception;

public class ApiException extends Exception{

    private int errorCode;

    private String errorMessage;

    public ApiException(Throwable throwable){
        super(throwable);
    }

    public ApiException(Throwable throwable,int errorCode,String errorMessage) {
        super(errorMessage,throwable);  //抛出错误的信息
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
