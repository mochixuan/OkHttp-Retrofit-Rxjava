package com.wx.rxretrofitlibrary.exception;

public class ErrorCode {

    //网络错误
    public static final int NETWORD_ERROR_CODE = 0x01;
    //http错误
    public static final int HTTP_ERROR_CODE = 0x02;
    //无法解析域名
    public static final int UNKNOWNHOST_ERROR_CODE = 0x03;

    //数据为空 和 服务器错误加在这里0x1-
    public static final int DATANULL_ERROR_CODE = 0x10;

    //运行时错误
    public static final int RUNTIME_ERROR_CODE = 0x20;

    //未知错误
    public static final int UNKNOWN_ERROR_CODE = 0x30;


    //-------------------------

    //网络错误
    public static final String NETWORD_ERROR_MSG = "网络错误";
    //http错误
    public static final String HTTP_ERROR_MSG = "Http错误";
    //无法解析域名
    public static final String UNKNOWNHOST_ERROR_MSG = "无法解析域名";

    //数据为空 和 服务器错误加在这里0x1-
    public static final String DATANULL_ERROR_MSG = "数据为空";

    //运行时错误
    public static final String RUNTIME_ERROR_MSG = "运行时错误";

    //未知错误
    public static final String UNKNOWN_ERROR_MSG = "未知错误";


}
