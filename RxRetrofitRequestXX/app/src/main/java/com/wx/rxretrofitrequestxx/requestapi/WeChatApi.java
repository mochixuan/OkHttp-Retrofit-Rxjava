package com.wx.rxretrofitrequestxx.requestapi;

import com.wx.rxretrofitlibrary.api.BaseApi;
import com.wx.rxretrofitrequestxx.constant.DataConstant;
import com.wx.rxretrofitrequestxx.service.RequestService;

import retrofit2.Retrofit;
import rx.Observable;

public class WeChatApi extends BaseApi{

    private String area;
    private String dtype;
    private String key;

    public WeChatApi(String area,String dtype,String key) {
        setBaseUrl(DataConstant.BASE_URL);
        this.area = area;
        this.dtype = dtype;
        this.key = key;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        RequestService requestService = retrofit.create(RequestService.class);
        return requestService.getMusicStr(area,dtype,key);
    }

}
