package com.wx.rxretrofitlibrary.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

public class FactoryException {

    public static ApiException analyExcetpion(Throwable e) {

        ApiException apiException = new ApiException(e);

        if (e instanceof HttpException) {                                                           //网络异常
            apiException.setErrorCode(ErrorCode.NETWORD_ERROR_CODE);
            apiException.setErrorMessage(ErrorCode.NETWORD_ERROR_MSG);
        } else if (e instanceof ConnectException ||e instanceof SocketTimeoutException) {           //链接异常
            apiException.setErrorCode(ErrorCode.HTTP_ERROR_CODE);
            apiException.setErrorMessage(ErrorCode.HTTP_ERROR_MSG);
        } else if (e instanceof UnknownHostException){                                              //无法解析该域名异常
            apiException.setErrorCode(ErrorCode.UNKNOWNHOST_ERROR_CODE);
            apiException.setErrorMessage(ErrorCode.UNKNOWNHOST_ERROR_MSG);
        } else if (e instanceof HttpRunException){                                                  //所以的服务器错误
            switch (((HttpRunException) e).getResultCode()) {
                case ErrorCode.DATANULL_ERROR_CODE:                                                 //数据为空
                    apiException.setErrorCode(ErrorCode.DATANULL_ERROR_CODE);
                    apiException.setErrorMessage(ErrorCode.DATANULL_ERROR_MSG);
                    break;
                default:
                    apiException.setErrorCode(ErrorCode.RUNTIME_ERROR_CODE);
                    apiException.setErrorMessage(ErrorCode.RUNTIME_ERROR_MSG);
                    break;
            }
        }else {                                                                                    //未知异常
            apiException.setErrorCode(ErrorCode.UNKNOWN_ERROR_CODE);
            apiException.setErrorMessage(e.getMessage());
            System.out.println("===============================>>"+e.getMessage());
        }

        return apiException;
    }

}
